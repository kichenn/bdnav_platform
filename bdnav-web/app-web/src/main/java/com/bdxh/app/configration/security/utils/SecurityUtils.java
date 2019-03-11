package com.bdxh.app.configration.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.PublicKey;

/**
 * @description: 用户信息工具类
 * @author: xuyuan
 * @create: 2019-02-28 16:22
 **/
public class SecurityUtils {

    public static PublicKey publicKey;

    static {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "zGe1MvGcgZPVvfctiZmecRg59tIXUcK24m".toCharArray());
        publicKey = keyStoreKeyFactory.getKeyPair("jwtTokenKeyPair").getPublic();
    }

    /**
     * 获取当前登录信息
     * @return
     */
    public static Claims getJwtClaims(){
        Claims claims = null;
        try {
            OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
            String tokenValue = oAuth2AuthenticationDetails.getTokenValue();
            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(tokenValue).getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return claims;
    }

}
