package com.app.device.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 通过类的方式获取
 *
 * @ConfigurationProperties的prefix配置前缀
 */
//@Component
//@ConfigurationProperties(prefix = "redis")
//@PropertySource(value = "classpath:redis.properties", encoding = "UTF-8")
@Configuration
@Data
public class RedisPropertiesConfig {

    // 与key值（去掉前缀后）相同的配置
    @Value("${redis.host}")
    private String host;
    // 与key值（去掉前缀后）相同的配置
    @Value("${redis.host}")
    private String port;
    // 与key值（去掉前缀后）对应，配置文件为test-a，java按照驼峰配置testA即可获取
    @Value("${redis.host}")
    private String auth;


}