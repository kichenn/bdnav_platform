package com.bdxh.app.configration.security;

import com.bdxh.app.configration.security.handler.MyExceptionTranslator;
import com.bdxh.app.configration.security.handler.MyAccessDeniedHandler;
import com.bdxh.app.configration.security.handler.MyUnauthorizedHandler;
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

    //http://localhost:9027/oauth/token?username=user_1&password=123456&grant_type=password&client_id=password&client_secret=123456

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
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            oauthServer.allowFormAuthenticationForClients();
        }

    }

}
