package com.lvhaifeng.cloud.auth.server.modules.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

/**
 * @author haifeng.lv
 * @create 2018/3/21.
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${resquest.skip}")
    private String matchers;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin()
                .and()
                .authorizeRequests().antMatchers(matchers.split(","))
                .permitAll().and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
