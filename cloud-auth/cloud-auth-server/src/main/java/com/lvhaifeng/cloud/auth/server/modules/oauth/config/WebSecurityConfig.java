package com.lvhaifeng.cloud.auth.server.modules.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .authorizeRequests().antMatchers("/static/**", "/bind", "/favicon.ico", "/webjars/**", "/client/**", "/v2/api-docs")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll();
    }

}
