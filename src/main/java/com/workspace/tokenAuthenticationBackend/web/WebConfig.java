package com.workspace.tokenAuthenticationBackend.web;

import com.workspace.tokenAuthenticationBackend.web.filter.LoginCheckFilter;
import com.workspace.tokenAuthenticationBackend.web.jwt.JWTTokenProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JWTTokenProvider jwtTokenProvider;
    @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter(jwtTokenProvider));
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/items");
        return filterRegistrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .exposedHeaders("x-access-token","x-refresh-token")
                .exposedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT");
    }

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginCheckInterceptor(jwtTokenProvider))
//                .order(2)
//                .addPathPatterns("/items");
//    }
}
