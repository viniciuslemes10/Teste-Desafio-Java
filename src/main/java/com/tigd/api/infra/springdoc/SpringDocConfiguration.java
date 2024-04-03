package com.tigd.api.infra.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Spring para a geração de documentação OpenAPI com Springdoc OpenAPI.
 *
 * Esta classe fornece configurações específicas do Spring necessárias para a geração de documentação OpenAPI
 * usando a biblioteca Springdoc OpenAPI.
 *
 * @author viniciuslemes10
 * @author gemeoslemes
 */

@Configuration
public class SpringDocConfiguration {
    /**
     * Método para criar e configurar um objeto OpenAPI personalizado.
     * Este método retorna uma instância de OpenAPI contendo informações sobre a API SF.api.
     * A API inclui funcionalidades de CRUD para clientes e empresas, além de transações.
     *
     * @return Um objeto OpenAPI configurado para a API SF.api
     */
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
