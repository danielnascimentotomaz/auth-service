package com.gft.auth.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

public class OpenApiConfig {


    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Auth Service")
                        .version("1.0.0")
                        .description("""
                                        API de autenticação responsável por gerenciar o ciclo de vida de usuários
                                        no sistema.Oferece endpoints seguros para registro, login  e  emissão  de 
                                        tokens JWT, garantindo controle de acesso baseado em roles e integração 
                                        com demais microserviços.
                                        """)
                        .contact(new Contact()
                                .name("Daniel Nascimento")
                                .email("Daniel.Tomaz@gft.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))

                );



    }
}
