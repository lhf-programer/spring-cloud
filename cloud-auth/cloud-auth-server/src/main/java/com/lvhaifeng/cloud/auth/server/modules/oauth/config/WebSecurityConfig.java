package com.lvhaifeng.cloud.auth.server.modules.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${resquest.skip}")
    private String matchers;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
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
