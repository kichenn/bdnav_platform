package com.bdxh.app.configration.security;

import com.bdxh.app.configration.security.handler.MyExceptionTranslator;
import com.bdxh.app.configration.security.handler.MyAccessDeniedHandler;
import com.bdxh.app.configration.security.handler.MyUnauthorizedHandler;
import com.bdxh.app.configration.security.properties.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @description: 授权服务配置类
 * @author: xuyuan
 * @create: 2019-03-07 10:35
 **/
@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfig {

    //获取token
    //http://localhost:9027/oauth/token?username=user_1&password=123456&grant_type=password&client_id=password&client_secret=123456

    //刷新token
    //http://localhost:9027/oauth/token?client_id=password&client_secret=123456&grant_type=refresh_token&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBwLXdlYiJdLCJkYXRhIjoi5rWL6K-V5pWw5o2uIiwidXNlcl9uYW1lIjoieHV5dWFuIiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjJmZTMwY2RkLTYwZGYtNGViNi1hNzUyLTRkYWUxYjc0MDZlOSIsImV4cCI6MTU1NDg3NzgyNSwianRpIjoiZDllNTI1MDMtNThjZC00NTA4LTkyYjgtYTdlOWZjMjM5MThmIiwiY2xpZW50X2lkIjoicGFzc3dvcmQifQ.KEx7sOlKNiGh7mXItON6H-_ll-D79nZ4hu7u8YAGKrarrIUEPBKLQ9LvPxggCKqwZJhnBUZmwwc7XOdk66Drs20InaT6AtFnez7DqnrB5_8AA4dCBv0m9eIc-hAqj9ClcH5bhl0TyrIMlAL2-zE9aKftfeEJx9xnyf6FoEft61l4EQ1B1OdLJEuHUN6IsXZRYztemT94UugJGn30vO03-QVNNRx_iHtjGMf8n7LuvRiWvs6kSFactQjQRtpJGt7zcPu2gbNTtr1bpaHtdt93XcIqapGy4NnU5wbUtZYEDGZG5R9ECuIvPej79PInXvaio_FpaGffxoZVdZOAPUVT_w

    //验证token
    //http://localhost:9027/oauth/check_token?token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBwLXdlYiJdLCJkYXRhIjoi5rWL6K-V5pWw5o2uIiwidXNlcl9uYW1lIjoieHV5dWFuIiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU1MjM5MDQ2NSwianRpIjoiYzY2NjA0ZTQtNjkxYy00YjliLWE5NjctODI1OGFhNjFjNmZiIiwiY2xpZW50X2lkIjoicGFzc3dvcmQifQ.hZsLWzq82F5I4bo_Y_NQTaRMvi8M8N7ZYecLEqmmBGxbgGeSky6VxTx9TpBDg_qZ0yZMwAffknE6JatTG9Wxz0zmD97Zbnk_NsoNZl2U7fLNOh8QLee-Oj-uBHuMuP0b_l9LIQJunkNV03B8Uiy7G_BYe8PWJKx7BWd1WVuUFJI08-v14VBcxesOBXqjpuutB9lmJqnKkfJZK2GqoN5OppEEa9O48jLMlofZ7J8vRZ60aAQ7UleypLGXrHYL0TZJYPsdqYUi-yWBTruE5-He2BGXhhDiz7VHwRgapZF4SMZ40PxXThqxTlzZlW1L2kTzQl2yOtghunEWcDkI7ZewoA

    //获取公钥
    //http://localhost:9027/oauth/token_key

    private static final String APP_WEB_RESOURCE_ID = "app-web";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private MyUnauthorizedHandler myUnauthorizedHandler;

        @Autowired
        private MyAccessDeniedHandler myAccessDeniedHandler;

        @Autowired
        @Qualifier("jwtTokenStore")
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(APP_WEB_RESOURCE_ID).stateless(true).tokenStore(tokenStore)
                    .authenticationEntryPoint(myUnauthorizedHandler).accessDeniedHandler(myAccessDeniedHandler);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().headers().frameOptions().disable()
                    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                    .antMatchers("/swagger-ui.html","/webjars/springfox-swagger-ui/**","/swagger-resources/**","/v2/api-docs/**").permitAll()
                    .anyRequest().authenticated()
                    .and().headers().cacheControl();
        }

    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private MyUnauthorizedHandler myUnauthorizedHandler;

        @Autowired
        private MyAccessDeniedHandler myAccessDeniedHandler;

        @Autowired
        private MyExceptionTranslator myExceptionTranslator;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        @Qualifier("jwtTokenStore")
        private TokenStore tokenStore;

        @Autowired
        private JwtAccessTokenConverter jwtAccessTokenConverter;


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            String secret = "{bcrypt}"+new BCryptPasswordEncoder().encode("123456");
            //配置两个客户端,一个用于password认证一个用于client认证
            clients.inMemory()
                    .withClient("client")
                    .resourceIds(APP_WEB_RESOURCE_ID)
                    .secret(secret)
                    .authorizedGrantTypes("client_credentials", "refresh_token")
                    .scopes("all")
                    .and().withClient("password")
                    .resourceIds(APP_WEB_RESOURCE_ID)
                    .secret(secret)
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("all");

        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints
                    .tokenStore(tokenStore)
                    .accessTokenConverter(jwtAccessTokenConverter)
                    .exceptionTranslator(myExceptionTranslator)
                    .authenticationManager(authenticationManager)
                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
            DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
            tokenServices.setTokenStore(endpoints.getTokenStore());
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setReuseRefreshToken(true);
            tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
            tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
            tokenServices.setAccessTokenValiditySeconds(SecurityConstant.TOKEN_VALIDITY_SECONDS);
            tokenServices.setRefreshTokenValiditySeconds(SecurityConstant.REFRESH_TOKEN_VALIDITY_SECONDS);
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.authenticationEntryPoint(myUnauthorizedHandler)
                    .accessDeniedHandler(myAccessDeniedHandler)
                    .checkTokenAccess("permitAll()")
                    .tokenKeyAccess("permitAll()")
                    .allowFormAuthenticationForClients();
        }

    }

}
