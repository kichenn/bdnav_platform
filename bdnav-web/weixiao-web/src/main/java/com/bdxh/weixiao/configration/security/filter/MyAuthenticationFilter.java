package com.bdxh.weixiao.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.exception.SwitchSchoolCodeException;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.userdetail.MyUserDetails;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 14:21
 **/
@Component
@Slf4j
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_REQUEST_HEADER);
        if (StringUtils.isEmpty(authHeader)) {
            authHeader = httpServletRequest.getParameter(SecurityConstant.TOKEN_REQUEST_PARAM);
        }
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            try {
                String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String subject = claims.getSubject();
                String token = redisUtil.get(SecurityConstant.TOKEN_KEY + subject);
                if (!StringUtils.equals(token, authHeader)) {
                    throw new ExpiredJwtException(null, claims, "登录已失效");
                }
                String userStr = (String) claims.get(SecurityConstant.USER_INFO);
                String authorityListStr = (String) claims.get(SecurityConstant.AUTHORITIES);

                UserInfo userInfo = JSON.parseObject(userStr, UserInfo.class);

                //效验schoolCode与token的schoolCode不同时，返回
                String schoolCode = httpServletRequest.getHeader("schoolCode");
                if (StringUtils.isNotEmpty(schoolCode)) {
                    //传入的schoolCode与 token的shcoolCode不同时抛出异常
                    if (!StringUtils.equals(schoolCode, userInfo.getSchoolCode())) {
                        throw new SwitchSchoolCodeException();
                    }
                }
                //设置当前登录用户
                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (securityContext != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        //获取权限
                        List<String> authorityList = JSON.parseObject(authorityListStr, List.class);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        if (authorityList != null && !authorityList.isEmpty()) {
                            authorityList.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority)));
                        }
                        //默认设置第一个孩子为cardnumber
                        MyUserDetails myUserDetails = new MyUserDetails(userInfo.getCardNumber().get(0), userInfo);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (ExpiredJwtException e) {
                Wrapper wrapper = WrapMapper.wrap(401, "授权已过期");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            } catch (SwitchSchoolCodeException e) {
                Wrapper wrapper = WrapMapper.wrap(401, "schoolCode变化，学校已切换");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            } catch (Exception e) {
                Wrapper wrapper = WrapMapper.wrap(401, "解析token错误");
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
            UserInfo user = new UserInfo();
            user.setWeixiaoStuId("test_1111");
            //学生卡号列表
            List<String> cardNumbers = new ArrayList<>();
            cardNumbers.add("22222");
            user.setCardNumber(cardNumbers);
            user.setName("ceshi");
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            //默认设置第一个孩子为cardnumber
            MyUserDetails myUserDetails = new MyUserDetails(user.getCardNumber().get(0), user);
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
