package com.tigd.api.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SF.api API")
                        .description("API Rest da aplicação SF.api, contendo as funcionalidades de CRUD de clientes e de empresas, além de transações podendo ser realizadas de ambas as partes.")
                        .contact(new Contact()
                                .name("Time backend")
                                .email(" vinikjhgfds@gmail.com, victorlemes0776@gmail.com")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://sf.bank/api/licenca")));
    }
}
