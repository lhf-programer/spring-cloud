package com.lvhaifeng.cloud.auth.server.modules.user.config;

import com.lvhaifeng.cloud.auth.server.modules.user.exception.AuthExceptionEntryPoint;
import com.lvhaifeng.cloud.auth.server.modules.user.exception.AccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Description 静态资源配置类
 * @Author haifeng.lv
 * @Date 2019/12/16 17:37
 */
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Value("${request.skip}")
    private String matchers;

    @Autowired
    private AccessHandler accessHandler;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint());
        resources.accessDeniedHandler(accessHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin()
                .and()
                .authorizeRequests().antMatchers(matchers.split(","))
                .permitAll().and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
