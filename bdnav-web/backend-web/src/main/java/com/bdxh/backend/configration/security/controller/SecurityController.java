package com.bdxh.backend.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.backend.configration.redis.RedisUtil;
import com.bdxh.backend.configration.security.properties.SecurityConstant;
import com.bdxh.backend.configration.security.userdetail.MyUserDetails;
import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.entity.User;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Kang
 * @create: 2019-02-28 10:47
 **/
@RestController
@Slf4j
@Api(value = "用户登录", tags = "用户登录交互API")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;


    @RequestMapping(value = "/authentication/login", method = RequestMethod.POST)
    @ApiOperation(value = "获取token", response = String.class)
    public void login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) throws IOException {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(username), "用户名不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = myUserDetails.getAuthorities();
            List<String> authorityList = new ArrayList<>();
            if (authorities != null && !authorities.isEmpty()) {
                authorities.forEach(authority -> authorityList.add(authority.getAuthority()));
            }
            Map<String, Object> claims = new HashMap<>(16);
            User user = myUserDetails.getUser();
            User userTemp = BeanMapUtils.map(user, User.class);
            userTemp.setPassword("");
            claims.put(SecurityConstant.USER, JSON.toJSONString(userTemp));
            claims.put(SecurityConstant.AUTHORITIES, JSON.toJSONString(authorityList));
            //登录成功生成token
            long currentTimeMillis = System.currentTimeMillis();
            redisUtil.setWithExpireTime(SecurityConstant.TOKEN_IS_REFRESH + username, DateUtil.format(new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME * 60 * 1000), "yyyy-MM-dd HH:mm:ss"), SecurityConstant.TOKEN_EXPIRE_TIME * 60);
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(user.getUserName())
                    .addClaims(claims)
                    .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            Wrapper wrapper = WrapMapper.ok(token);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        } catch (Exception e) {
            String message = e.getMessage();
            if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                message = "用户名或者密码不正确";
            }
            if (e instanceof LockedException) {
                message = "账户已被锁定";
            }
            Wrapper wrapper = WrapMapper.wrap(401, message);
            String str = JSON.toJSONString(wrapper);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write(str.getBytes("utf-8"));
        }
    }

    @GetMapping("/getUserInfoByToken")
    @ApiOperation(value = "token获取用户信息", response = String.class)
    public Object getUserInfoByToken() {
        return WrapMapper.ok(SecurityUtils.getCurrentUser());
    }


    @GetMapping("/getTokenTime")
    @ApiOperation(value = "token的有效时间", response = String.class)
    public Object getTokenTime(HttpServletRequest httpServletRequest, @RequestParam(name = "loseToken") String loseToken) {
        String authHeader = loseToken;
        if (authHeader != null && authHeader.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            String auth = authHeader.substring(SecurityConstant.TOKEN_SPLIT.length());
            try {
                Claims claims = Jwts.parser().setSigningKey(SecurityConstant.TOKEN_SIGN_KEY).parseClaimsJws(auth).getBody();
                String resultDate = DateUtil.format(claims.getExpiration(), "yyyy-MM-dd HH:mm:ss");
                return WrapMapper.ok(resultDate);
            } catch (ExpiredJwtException e) {
                return WrapMapper.ok(e.getClaims().getExpiration());
            }
        }
        return WrapMapper.error();
    }
}
