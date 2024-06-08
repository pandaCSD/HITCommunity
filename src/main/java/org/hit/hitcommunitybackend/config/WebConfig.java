package org.hit.hitcommunitybackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**")
            .addResourceLocations("classpath:/static/"); // 确保这里指向正确的目录
    registry.addResourceHandler("/images/**")
            .addResourceLocations("file:src/main/resources/static/images/"); // 确保这里指向上传的图片目录
  }
  
}

