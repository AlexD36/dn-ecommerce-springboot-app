package com.dn.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.dn.shop.config.JwtProperties;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.dn.shop.repository")
@EntityScan(basePackages = "com.dn.shop.model.entity")
@EnableConfigurationProperties(JwtProperties.class)
public class ShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
