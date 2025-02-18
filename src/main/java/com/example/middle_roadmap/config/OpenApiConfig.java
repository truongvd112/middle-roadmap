package com.example.middle_roadmap.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Middle Roadmap Common API",
                version = "1.0",
                description = "Middle Roadmap API"
        )
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "jwt_token",
                        scheme = "bearer",
                        bearerFormat = "JWT",
                        type = SecuritySchemeType.HTTP,
                        in = SecuritySchemeIn.HEADER
                )
        }
)
public class OpenApiConfig {
}
