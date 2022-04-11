package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


public class WebAppConfig implements WebMvcConfigurer {

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
////        registry.addViewController("/login/page").setViewName("login");
////        registry.addViewController("/login/welcome").setViewName("welcome");
////        registry.addViewController("/logout").setViewName("logoutResult");
////        registry.addViewController("/logout/page").setViewName("logout");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      // registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/resources/css/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
