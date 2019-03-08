package com.bdxh.app.configration.security.token;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @description: jwt配置类
 * @author: xuyuan
 * @create: 2019-03-07 17:03
 **/
@Configuration
public class JwtAccessTokenConfig {

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessToken converter = new JwtAccessToken();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "zGe1MvGcgZPVvfctiZmecRg59tIXUcK24m".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwtTokenKeyPair"));
        return converter;
    }

}
