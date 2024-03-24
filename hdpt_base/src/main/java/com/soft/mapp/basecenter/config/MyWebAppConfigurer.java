package com.soft.mapp.basecenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    /**
     * 上传地址
     */
    @Value("${file.image.path}")
    private String imagePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取当前目录路径
        String currentDirectoryPath = System.getProperty("user.dir");
        // 将当前目录路径转换为File对象
        //File currentDirectory = new File(currentDirectoryPath);
        // 获取上级目录路径
        //String parentDirectoryPath = currentDirectory.getParent();
        // 将上级目录路径转换为File对象
        //File parentDirectory = new File(parentDirectoryPath);
        // 获取上级目录的绝对路径
        //String parentDirectoryAbsolutePath = parentDirectory.getAbsolutePath();
        //文件磁盘图片url 映射
        //配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + currentDirectoryPath+ imagePath);

    }

}
