package com.gft.auth.controller;


import com.gft.auth.dto.LoginRequest;
import com.gft.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Autenticação", description = "Endpoints responsáveis por login e registro de usuários.")
public interface AuthApi {

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria um usuário no sistema e atribui roles automaticamente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso."),
            @ApiResponse(
                    responseCode = "409",
                    description = "Usuário já existe.",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    ResponseEntity<?> register(RegisterRequest request);

    @Operation(
            summary = "Autenticar usuário",
            description = "Realiza login com usuário e senha e retorna um token JWT."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Autenticado com sucesso.",
                    content = @Content(schema = @Schema(implementation = Map.class))
            ),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    })
    ResponseEntity<?> login(LoginRequest request);
}