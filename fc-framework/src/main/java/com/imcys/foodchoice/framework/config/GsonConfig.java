package com.imcys.foodchoice.framework.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 替换输出解析类，改用GSON完成
 */
@Configuration
public class GsonConfig {

//    @Bean
//    public HttpMessageConverters messageConverters() {
//
//        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//
//        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
//        messageConverters.add(gsonHttpMessageConverter);
//
//        return new HttpMessageConverters(true, messageConverters);
//    }
}
