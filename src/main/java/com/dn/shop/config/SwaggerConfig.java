package com.dn.shop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI shopOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Shop API Documentation")
                .description("REST API documentation for the Shop application")
                .version("1.0")
                .contact(new Contact()
                    .name("Shop Team")
                    .email("support@shop.com")));
    }
} 