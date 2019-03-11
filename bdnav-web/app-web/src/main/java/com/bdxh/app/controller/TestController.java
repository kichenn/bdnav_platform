package com.bdxh.app.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 测试控制器
 * @author: xuyuan
 * @create: 2019-03-07 17:28
 **/
@RestController
@RequestMapping("/order")
public class TestController {

    @RequestMapping("hello")
    public Object hello(Authentication authentication){
        String tokenValue = ((OAuth2AuthenticationDetails)authentication.getDetails()).getTokenValue();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "zGe1MvGcgZPVvfctiZmecRg59tIXUcK24m".toCharArray());
        Claims body = Jwts.parser().setSigningKey(keyStoreKeyFactory.getKeyPair("jwtTokenKeyPair").getPublic()).parseClaimsJws(tokenValue).getBody();
        return "xy";
    }

}
