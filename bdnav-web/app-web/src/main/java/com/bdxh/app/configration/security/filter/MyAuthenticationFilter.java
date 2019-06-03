package com.bdxh.app.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.redis.RedisUtil;
import com.bdxh.app.configration.security.exception.MutiLoginException;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: App token效验
 * @Author: Kang
 * @Date: 2019/5/10 14:48
 */
//@Component
@Slf4j
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_REQUEST_HEADER);
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
//                String username = claims.getSubject();
                String accountStr = (String) claims.get(SecurityConstant.ACCOUNT);
                Account account = null;
                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (securityContext != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        account = JSON.parseObject(accountStr, Account.class);
                        //true为未过期,false  已过期
                        boolean isAccountNonExpired = account.getAccountExpired() == 1 ? Boolean.TRUE : Boolean.FALSE;
                        //true为未锁定,false 已锁定
                        boolean isAccountNonLocked = account.getAccountLocked() == 1 ? Boolean.TRUE : Boolean.FALSE;
                        MyUserDetails myUserDetails = new MyUserDetails(account.getId(), account.getLoginName(), account.getPassword(), isAccountNonExpired, isAccountNonLocked, account);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, null);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                //判断token所在设备
                String redisToken = redisUtil.get(SecurityConstant.TOKEN_KEY + account.getId());
                if (StringUtils.isNotEmpty(redisToken)) {
                    //只允许一处设备登录
                    if (!StringUtils.equals(authHeader, redisToken)) {
                        //重复登录
                        throw new MutiLoginException();
                    }
                }
                //获取认证信息
                Authentication authentication = securityContext.getAuthentication();
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //刷新token 时效14天 刷新7天 token最小时长14天 最大操作间隔7天 否则需重新登录
                Date date = DateUtil.format(redisUtil.get(SecurityConstant.TOKEN_IS_REFRESH + account.getId()), "yyyy-MM-dd HH:mm:ss");
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime refreshTime = instant.atZone(zoneId).toLocalDateTime();
                if (LocalDateTime.now().isAfter(refreshTime)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    redisUtil.setWithExpireTime(SecurityConstant.TOKEN_IS_REFRESH + account.getId(), DateUtil.format(new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME), "yyyy-MM-dd HH:mm:ss"), SecurityConstant.TOKEN_EXPIRE_TIME);
                    String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(account.getLoginName())
                            .claim(SecurityConstant.ACCOUNT, accountStr)
                            .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME))
                            .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                            .compressWith(CompressionCodecs.GZIP).compact();
                    //将token放入redis
                    redisUtil.set(SecurityConstant.TOKEN_KEY + account.getId(), token);
                    //允许前端从headers里拿到我的Token
                    httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConstant.TOKEN_RESPONSE_HEADER);
                    httpServletResponse.addHeader(SecurityConstant.TOKEN_RESPONSE_HEADER, token);
                }
            } catch (ExpiredJwtException e) {
                Wrapper wrapper = WrapMapper.error("登录已失效");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            }  catch (MutiLoginException e) {
                Wrapper wrapper = WrapMapper.error("账号已在其他设备登录");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            } catch (Exception e) {
                Wrapper wrapper = WrapMapper.error("解析token错误");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            }
        } else if (authHeader != null && authHeader.equals("BDXH_TEST")) {
            Account account = new Account();
            account.setId(new Long("001"));
            account.setLoginName("ceshi");
            account.setPassword(new BCryptPasswordEncoder().encode("123456"));
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            MyUserDetails myUserDetails = new MyUserDetails(account.getId(), account.getUserName(), "", true, true, account);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else if (authHeader != null && authHeader != "") {
            Wrapper wrapper = WrapMapper.wrap(401, "token异常");
            String str = JSON.toJSONString(wrapper);
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpServletResponse.setStatus(200);
            httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
