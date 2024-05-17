package com.cause15.issuetrackerserver.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Swagger test")
                .version("1.0")
                .description("API description");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
