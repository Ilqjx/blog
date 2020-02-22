package com.upfly.interceptor;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**") // 过滤'/admin/**'路径
                .excludePathPatterns("/admin") // 排除'/admin'路径
                .excludePathPatterns("/admin/login");
        super.addInterceptors(registry);
    }

    @Override
    // 添加静态资源的位置
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
