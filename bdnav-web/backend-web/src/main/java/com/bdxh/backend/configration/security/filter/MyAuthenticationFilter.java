package com.bdxh.backend.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.backend.configration.security.userdetail.MyUserDetails;
import com.bdxh.backend.configration.security.properties.SecurityConstant;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String token = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                SecurityContext securityContext  = SecurityContextHolder.getContext();
                if (securityContext != null){
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null){
                        Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(token).getBody();
                        String username = claims.getSubject();
                        User user = JSON.parseObject((String) claims.get(SecurityConstant.USER),User.class);
                        List<String> authorityList = JSON.parseObject((String)claims.get(SecurityConstant.AUTHORITIES),List.class);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        if (authorityList != null && !authorityList.isEmpty()){
                            authorityList.forEach(authority->authorities.add(new SimpleGrantedAuthority(authority)));
                        }
                        MyUserDetails myUserDetails = new MyUserDetails(username,"",true,authorities,user);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            }catch (ExpiredJwtException e) {
                Wrapper wrapper= WrapMapper.error("登录已失效");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            } catch (Exception e){
                Wrapper wrapper= WrapMapper.error("解析token错误");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                return;
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
