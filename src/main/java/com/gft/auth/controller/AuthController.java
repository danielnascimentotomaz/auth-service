package com.gft.auth.controller;


import com.gft.auth.dto.LoginRequest;
import com.gft.auth.dto.RegisterRequest;
import com.gft.auth.model.User;
import com.gft.auth.repository.UserRepository;
import com.gft.auth.security.JwtUtil;
import com.gft.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController implements AuthApi{

    private final UserService userService;

   ;


    public AuthController(UserService userService) {
        this.userService = userService;

    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
            userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário "+ request.username() +  " criado Com sucesso");

    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Map<String, String> tokenMap = userService.login(request);
        return ResponseEntity.ok(tokenMap);
    }
}
