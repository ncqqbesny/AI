package com.hdpt.device.config;

import com.hdpt.device.services.INetDeviceQueryService;
import com.hdpt.device.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {
    /**
     * 静态资源的配置 - 使得可以从磁盘中读取 Html、图片、视频、音频等
     */
    @Autowired
    INetDeviceQueryService netDeviceQueryService;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
            配置server虚拟路径，handler为前台访问的URL目录，locations为files相对应的本地路径
            也就是说如果有一个 upload/avatar/aaa.png 请求，那程序会到后面的目录里面找aaa.png文件
            另外：如果项目中有使用Shiro，则还需要在Shiro里面配置过滤下
         */
        String absolutePath="";
        List<Map> mapList=netDeviceQueryService.getSystemParam("firex_Identify_config");
        for(Map map:mapList) {
            String paramCode= ConvertUtils.toString(map.get("param_code"));
            String paramVal=ConvertUtils.toString(map.get("param_val"));
            if(paramCode.equalsIgnoreCase("pic_path") ){
                absolutePath=paramVal;
            }
        }
        registry.addResourceHandler("/uploadImages/**").addResourceLocations("file:"+absolutePath+ "/");


    }
}

