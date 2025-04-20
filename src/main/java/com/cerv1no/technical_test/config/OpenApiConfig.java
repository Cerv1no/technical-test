package com.cerv1no.technical_test.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Onebox technical test",
                version = "1.0",
                description = "Cart API documentation",
                contact = @Contact(
                        name = "Álvaro Álvarez Cervino",
                        email = "alvaro.alvarez.cervino@gmail.com",
                        url = "https://www.linkedin.com/in/alvaro-cerv1no/"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Entorno Local")
        }
)
public class OpenApiConfig {
}
