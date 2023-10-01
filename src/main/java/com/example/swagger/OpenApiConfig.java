package com.example.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Weather Api",
                description = "Get Weather", version = "1.0.0",
                contact = @Contact(
                        name = "Shchetinin Stanislav"
                )
        )
)
public class OpenApiConfig {
}
