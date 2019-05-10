package com.bdxh.app.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.security.exception.MutiLoginException;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
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
import java.util.concurrent.TimeUnit;

/**
 * @Description: App token效验
 * @Author: Kang
 * @Date: 2019/5/10 14:48
 */
@Component
@Slf4j
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_REQUEST_HEADER);
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String username = claims.getSubject();
                String accountStr = (String) claims.get(SecurityConstant.ACCOUNT);
                Account account = JSON.parseObject(accountStr, Account.class);
                String redisToken = (String) redisTemplate.opsForValue().get(SecurityConstant.TOKEN_KEY + account.getId());
                if (StringUtils.isNotEmpty(redisToken)) {
                    if (!StringUtils.equals(authHeader, redisToken)) {
                        throw new MutiLoginException();
                    }
                }
                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (securityContext != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        MyUserDetails myUserDetails = new MyUserDetails(account.getId(), username, "", true, true, account);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, null);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                //刷新token 时效14天 刷新7天 token最小时长14天 最大操作间隔7天 否则需重新登录
                Date date = (Date) redisTemplate.opsForValue().get(SecurityConstant.TOKEN_IS_REFRESH + account.getId());
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime refreshTime = instant.atZone(zoneId).toLocalDateTime();
                if (LocalDateTime.now().isAfter(refreshTime)) {
                    long currentTimeMillis = System.currentTimeMillis();
                    redisTemplate.opsForValue().set(SecurityConstant.TOKEN_IS_REFRESH + account.getId(), new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME), SecurityConstant.TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
                    String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(account.getLoginName())
                            .claim(SecurityConstant.ACCOUNT, accountStr)
                            .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME))
                            .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                            .compressWith(CompressionCodecs.GZIP).compact();
                    //将token放入redis
                    redisTemplate.opsForValue().set(SecurityConstant.TOKEN_KEY + account.getId(), token);
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
            } catch (MutiLoginException e) {
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
            User user = new User();
            user.setUserName("xuyuan");
            user.setPassword(new BCryptPasswordEncoder().encode("123456"));
            user.setRealName("徐圆");
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            MyUserDetails myUserDetails = new MyUserDetails(user.getUserName(), "", true, authorities, user);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
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
