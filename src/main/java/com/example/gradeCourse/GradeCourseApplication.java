package com.example.gradeCourse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GradeCourseApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(GradeCourseApplication.class, args);
    }

    @Value("${file-save-path}")
    private String fileSavePath;

    @Value("${excel-save-path}")
    private String excelSavePath;

    @Value("${img-save-path}")
    private String imgSavePath;

    @Value("${avatar-save-path}")
    private String avatarSavePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
        http://localhost:8080/img/0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg
        下面配置的大概意思就是，只有在url中是以img开头（如：http://localhost:8080/img）
        就到文件夹中如（F:\fff\img\）找到对应的图片或视频
         */
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + imgSavePath);
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:" + avatarSavePath);
        registry.addResourceHandler("/uploadFile/**").addResourceLocations("file:" + fileSavePath);
        registry.addResourceHandler("/excel/**").addResourceLocations("file:" + excelSavePath);
    }

}
