package com.bdxh.weixiao.configration.security.filter;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.properties.SecurityConstant;
import com.bdxh.weixiao.configration.security.utils.SecurityContext;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String servletPath = httpServletRequest.getServletPath();
        //静态资源直接放行不进行拦截
        if (!StringUtils.equals(servletPath,"/favicon.ico")&&!StringUtils.endsWithAny(".html")&&!StringUtils.equals(servletPath,SecurityConstant.AUTHENTICATION_URL)){
            String authHeader = httpServletRequest.getHeader(SecurityConstant.TOKEN_REQUEST_HEADER);
            if (StringUtils.isEmpty(authHeader)){
                authHeader = httpServletRequest.getParameter(SecurityConstant.TOKEN_REQUEST_PARAM);
            }
            try {
                Preconditions.checkArgument(StringUtils.isNotEmpty(authHeader) && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT),"token不正确");
                String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
                Header header = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getHeader();
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String subject = claims.getSubject();
                String token  = (String)redisTemplate.opsForValue().get(SecurityConstant.TOKEN_KEY + subject);
                if (!StringUtils.equals(token,authHeader)){
                    throw new ExpiredJwtException(header,claims,"登录已失效");
                }
                String userInfoStr = (String) claims.get(SecurityConstant.USER_INFO);
                String[] data = StringUtils.split(userInfoStr, "_");
                UserInfo userInfo = new UserInfo();
                userInfo.setSchoolCode(data[0]);
                userInfo.setName(data[1]);
                userInfo.setCardNumber(data[2]);
                userInfo.setSchoolCode(data[3]);
                //设置当前登录用户
                SecurityContext.setUserInfo(userInfo);
            }catch (ExpiredJwtException e) {
                Wrapper wrapper = WrapMapper.error("登录已失效");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                SecurityContext.removeUserInfo();
                return;
            }
            catch (Exception e) {
                Wrapper wrapper = WrapMapper.error("未登录系统");
                String str = JSON.toJSONString(wrapper);
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setStatus(401);
                httpServletResponse.setHeader("Content-type", "application/json; charset=UTF-8");
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=utf-8");
                httpServletResponse.getOutputStream().write(str.getBytes("utf-8"));
                SecurityContext.removeUserInfo();
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        //清除当前登录用户
        SecurityContext.removeUserInfo();
    }

}
