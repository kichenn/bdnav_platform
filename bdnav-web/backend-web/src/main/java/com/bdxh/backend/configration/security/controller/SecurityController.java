package com.bdxh.backend.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.backend.configration.security.userdetail.MyUserDetails;
import com.bdxh.backend.configration.security.properties.SecurityConstant;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @author: xuyuan
 * @create: 2019-02-27 22:37
 **/
@RestController
@RequestMapping("/authentication")
@Slf4j
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Preconditions.checkArgument(StringUtils.isNotEmpty(username),"用户名不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(password),"密码不能为空");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails)authenticate.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = myUserDetails.getAuthorities();
            List<String> authorityList = new ArrayList<>();
            if (authorities != null && !authorities.isEmpty()){
                authorities.forEach(authority->authorityList.add(authority.getAuthority()));
            }
            Map<String,Object> claims = new HashMap<>(16);
            User user = myUserDetails.getUser();
            User userTemp = BeanMapUtils.map(user, User.class);
            userTemp.setPassword("");
            claims.put(SecurityConstant.USER,JSON.toJSONString(userTemp));
            claims.put(SecurityConstant.AUTHORITIES,JSON.toJSONString(authorityList));
            //登录成功生成token
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(user.getUserName())
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            Wrapper wrapper= WrapMapper.ok(token);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(401);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        }catch (Exception e){
            String message = e.getMessage();
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
                message = "用户名或者密码不正确";
            }
            if (e instanceof LockedException){
                message = "账户已被锁定";
            }
            Wrapper wrapper= WrapMapper.error(message);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(401);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        }
    }

}
