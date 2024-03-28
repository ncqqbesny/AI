package com.hdpt.device.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
public class Knife4jConfig {
    @Bean
    public Docket docket(Environment environment) {

        //设置只在开发中环境中启动swagger
        Profiles profiles=Profiles.of("dev");

        //表示如果现在是dev环境，则返回true 开启swagger
        boolean flag=environment.acceptsProfiles(profiles);
        //始终打开文档界面
        flag=true;
        //添加接口请求头参数配置 没有的话 可以忽略
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("令牌").defaultValue("V5IN62D9MH9KA3Y031NW2QDTC2Z0PN2R6IJXCKK3ZZOM5PMZ85").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                //是否启动swagger 默认启动
                .enable(flag)
                //所在分组
                .groupName("硬件维护平台设备接口")
                .select()
                //指定扫描的包路径
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.hdpt.device.impl.controller")
                        ,RequestHandlerSelectors.basePackage("com.hdpt.device.handler")
                ))
                //指定扫描的请求，这里表示扫描 /hello/ 的请求
                //.paths(PathSelectors.ant("/hello/**"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("硬件维护平台设备接口文档")
                .contact(new Contact("yzg", "https://github.com.clm", "258481720@qq.com"))
                .version("v1.1.0")
                .title("HTTP API接口文档")
                .build();
    }
}
