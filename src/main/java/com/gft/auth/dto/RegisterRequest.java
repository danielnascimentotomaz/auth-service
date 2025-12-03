package com.gft.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(
        name = "RegisterRequest",
        description = "Objeto utilizado para registrar um novo usuário no sistema."
)
public record RegisterRequest(

        @Schema(
                description = "Nome de usuário (login) a ser criado.",
                example = "Daniel Nascimento Tomaz"
        )
        String username,

        @Schema(
                description = "Senha do usuário.",
                example = "123456"
        )
        String password,

        @Schema(
                description = "Lista de roles que o usuário terá. Caso não seja enviada, o sistema pode atribuir automaticamente.",
                example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]"
        )
        Set<String> roles

) {}