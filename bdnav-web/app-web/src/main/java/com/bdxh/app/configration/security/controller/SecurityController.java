package com.bdxh.app.configration.security.controller;

import com.alibaba.fastjson.JSON;
import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.redis.RedisUtil;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.google.common.base.Preconditions;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 权限控制相关
 * @Author: Kang
 * @Date: 2019/5/10 15:01
 */
@RestController
@Api(value = "app用户登录信息", tags = "app用户登录信息")
@Slf4j
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtil redisUtil;


    @ApiOperation(value = "获取token", response = Boolean.class)
    @RequestMapping(value = "/authenticationApp/login", method = RequestMethod.GET)
    public void login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) throws IOException {
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(username), "用户名不能为空");
            Preconditions.checkArgument(StringUtils.isNotEmpty(password), "密码不能为空");
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
            Account account = myUserDetails.getAccount();
            Account accountTemp = BeanMapUtils.map(account, Account.class);
            accountTemp.setPassword("");
            String accountStr = JSON.toJSONString(accountTemp);
            //登录成功生成token
            long currentTimeMillis = System.currentTimeMillis();
            redisUtil.setWithExpireTime(SecurityConstant.TOKEN_IS_REFRESH + account.getId(), DateUtil.format(new Date(currentTimeMillis + SecurityConstant.TOKEN_REFRESH_TIME), "yyyy-MM-dd HH:mm:ss"), SecurityConstant.TOKEN_EXPIRE_TIME);
            String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder().setSubject(account.getLoginName())
                    .claim(SecurityConstant.ACCOUNT, accountStr)
                    .setExpiration(new Date(currentTimeMillis + SecurityConstant.TOKEN_EXPIRE_TIME))
                    .signWith(SignatureAlgorithm.HS512, SecurityConstant.TOKEN_SIGN_KEY)
                    .compressWith(CompressionCodecs.GZIP).compact();
            //将token放入redis
            redisUtil.set(SecurityConstant.TOKEN_KEY + myUserDetails.getId(), token);
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
            if (e instanceof AccountExpiredException) {
                message = "账户已过期";
            }
            if (e instanceof LockedException) {
                message = "账户已被锁定";
            }
            Wrapper wrapper = WrapMapper.error(message);
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
