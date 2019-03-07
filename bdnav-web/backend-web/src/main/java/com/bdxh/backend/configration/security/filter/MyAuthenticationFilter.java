package com.bdxh.backend.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.backend.configration.security.userdetail.MyUserDetails;
import com.bdxh.backend.configration.security.properties.SecurityConstant;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-28 14:21
 **/
@Component
@Slf4j
public class MyAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_REQUEST_HEADER);
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String username = claims.getSubject();
                String userStr = (String) claims.get(SecurityConstant.USER);
                String authorityListStr = (String) claims.get(SecurityConstant.AUTHORITIES);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                if (securityContext != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication == null) {
                        User user = JSON.parseObject(userStr, User.class);
                        List<String> authorityList = JSON.parseObject(authorityListStr, List.class);
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        if (authorityList != null && !authorityList.isEmpty()) {
                            authorityList.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority)));
                        }
                        MyUserDetails myUserDetails = new MyUserDetails(username, "", true, authorities, user);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUserDetails, null, authorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
                //刷新token 时效2小时 刷新1小时 token最小时长2小时 最大操作间隔1小时 否则需重新登录
                Date date = (Date) redisTemplate.opsForValue().get(SecurityConstant.TOKEN_IS_REFRESH + username);
                Instant instant = date.toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime refreshTime = instant.atZone(zoneId).toLocalDateTime();
                if (LocalDateTime.now().isAfter(refreshTime)){
                    Map<String,Object> param = new HashMap<>(16);
                    param.put(SecurityConstant.USER,userStr);
                    param.put(SecurityConstant.AUTHORITIES,authorityListStr);
                    long currentTimeMillis = System.currentTimeMillis();
                    redisTemplate.opsForValue().set(SecurityConstant.TOKEN_IS_REFRESH+username,new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME * 60 * 1000),SecurityConstant.TOKEN_EXPIRE_TIME, TimeUnit.MINUTES);
                    String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(username)
                            .addClaims(param)
                            .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                            .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                            .compressWith(CompressionCodecs.GZIP).compact();
                    httpServletResponse.addHeader(SecurityConstant.TOKEN_RESPONSE_HEADER,token);
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
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
