package com.spider.dic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.spider.dic.dao")
@EntityScan(basePackages = "com.spider.dic.entity")
public class DicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DicApplication.class, args);
	}
}