package com.bdxh.weixiao.configration.security;

import com.bdxh.weixiao.configration.security.filter.MyAuthenticationFilter;
import com.bdxh.weixiao.configration.security.handler.MyAccessDeniedHandler;
import com.bdxh.weixiao.configration.security.handler.MyUnauthorizedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
* @Description: security配置类
* @Author: Kang
* @Date: 2019/5/14 16:52
*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUnauthorizedHandler myUnauthorizedHandler;

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

//    @Autowired
//    private MyUserDetailsService myUserDetailsService;

    @Bean
    static BCryptPasswordEncoder getBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }


    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/public/**", "/static/**", "/resources/**", "/META-INF/resources/**");
    }

//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(myUserDetailsService).passwordEncoder(getBCryptPasswordEncoder());
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().headers().frameOptions().disable()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()//图标放行
                .antMatchers("/*.html").permitAll() //静态资源放行
                .antMatchers("/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**").permitAll()
                .antMatchers("/authenticationWeixiao/**").permitAll()
                .antMatchers("/MP_verify_fL8hJJpPmRU0cZCm.txt").permitAll()
                .anyRequest().authenticated()//其他路径进行权限验证
                .and().headers().cacheControl();
        //设置jwt filter
        httpSecurity.addFilterAt(myAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.exceptionHandling().authenticationEntryPoint(myUnauthorizedHandler).accessDeniedHandler(myAccessDeniedHandler);
    }

    @Bean
    public MyAuthenticationFilter myAuthenticationFilterBean() {
        return new MyAuthenticationFilter();
    }
}
