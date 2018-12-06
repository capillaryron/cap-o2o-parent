package com.cap.o2o.schedu.config;


import com.cap.o2o.schedu.interceptor.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by ron on 2018/12/3.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private MethodInterceptor methodInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(methodInterceptor).addPathPatterns("/**");
    }

}

