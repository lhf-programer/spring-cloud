package com.lvhaifeng.cloud.auth.server.modules.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Description security 配置类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${request.skip}")
    private String matchers;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean() ;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .authorizeRequests().antMatchers(matchers.split(","))
            .permitAll()
            .and()
            .authorizeRequests().antMatchers(HttpMethod.OPTIONS)
            .permitAll()
            .and()
            .formLogin();
    }

}
