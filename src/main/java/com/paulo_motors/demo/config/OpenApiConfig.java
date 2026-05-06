package com.paulo_motors.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Paulo Motor's API")
                        .version("1.0")
                        .description("Car rental management system API")
                        .contact(new Contact()
                                .name("Paulo Maggio")
                                .email("paulo@paulomotors.com")));
    }
}