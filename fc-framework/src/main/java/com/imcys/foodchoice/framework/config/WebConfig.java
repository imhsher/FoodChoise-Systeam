package com.imcys.foodchoice.framework.config;


import com.imcys.foodchoice.common.convertor.EnumConverter;
import com.imcys.foodchoice.common.enu.SearchType;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${foodchoice.file-upload-path}")
    private String fileUploadPath;

    @Override
    public void addFormatters(@NotNull FormatterRegistry registry) {
        registry.addConverter(new EnumConverter<>(SearchType.class));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:"+fileUploadPath);
    }
}
