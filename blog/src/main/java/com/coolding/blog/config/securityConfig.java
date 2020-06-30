package com.coolding.blog.config;

import org.apache.catalina.security.SecurityConfig;
import org.springframework.boot.autoconfigure.security.servlet.SpringBootWebSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName securityConfig
 * @Author 酷酷的丁
 * @Date 2020-03-30 13:15
 */
@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/lib/**","/css/**","/blogPage/css/**","/blogPage/js/**","/blogPage/img/**","/static/**").permitAll();

    }
}
