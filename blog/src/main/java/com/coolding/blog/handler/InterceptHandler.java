package com.coolding.blog.handler;
import com.coolding.blog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.annotation.Resource;


@Configuration
public class InterceptHandler implements WebMvcConfigurer   {

    /*
          拦截器，让过滤器能忽略静态资源 /static/**
        * */
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }

  @Override
      //登录拦截器
      public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/admin/**")
                    .excludePathPatterns("/admin","/static/**","/css/**","/img/**","/js/**")
                    .excludePathPatterns("/admin/login");
        }

    }
