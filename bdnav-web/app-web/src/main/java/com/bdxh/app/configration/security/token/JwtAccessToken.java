package com.bdxh.app.configration.security.token;

import com.bdxh.app.configration.security.properties.SecurityConstant;
import com.bdxh.app.configration.security.userdetail.MyUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @description: jwt自定义令牌
 * @author: xuyuan
 * @create: 2019-03-07 18:50
 **/
public class JwtAccessToken extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        Object data = ((MyUserDetails) authentication.getPrincipal()).getData();
        defaultOAuth2AccessToken.getAdditionalInformation().put(SecurityConstant.DATA_KEY, data);
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }

    @Override
    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map){
        OAuth2AccessToken oauth2AccessToken = super.extractAccessToken(value, map);
        oauth2AccessToken.getAdditionalInformation().put(SecurityConstant.DATA_KEY,map.get(SecurityConstant.DATA_KEY));
        return oauth2AccessToken;
    }

}
