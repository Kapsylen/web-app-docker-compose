package dev.sebsve.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiSwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String locUrl = "http://localhost:8080";
        final String devUrl = "https://.de";
        final String testUrl = "https://.de";
        final String preUrl = "https://.de";
        final String proUrl = "https://.grp";

        return new OpenAPI().addServersItem(new Server().url(locUrl)).addServersItem(new Server().url(
                        devUrl)).addServersItem(new Server().url(testUrl)).addServersItem(new Server().url(preUrl))
                .addServersItem(new Server().url(proUrl)).info(
                        new Info().version("v1").title("User API")
                                .description("User API - create, read, update, delete and delete users")
                                .contact(new Contact().name("Edi")));
    }
}
