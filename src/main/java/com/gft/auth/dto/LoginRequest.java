package com.gft.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Objeto contendo as credenciais de autenticação do usuário.")
public record LoginRequest(

        @Schema(
                description = "Nome de usuário utilizado para autenticação.",
                example = "daniel_nascimento"

        )
        String username,


        @Schema(
                description = "Senha do usuário.",
                example = "123456"
        )
        String password

) {}
