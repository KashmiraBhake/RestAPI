package com.restapikarkinos.WebappApi;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageResourceHandler implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("./patient-photos");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/patient-photos/**").addResourceLocations("file://" + uploadPath + "//");

    }

}