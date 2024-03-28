package com.soft.mapp.basecenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@MapperScan("com.soft.mapp.basecenter.dao")
public class basecenterApplication {

	public static void main(String[] args) {
		//System.setProperty("nacos.logging.default.config.enabled","false");
		SpringApplication.run(basecenterApplication.class, args);

	}

}
