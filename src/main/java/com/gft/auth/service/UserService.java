package com.gft.auth.service;

import com.gft.auth.dto.LoginRequest;
import com.gft.auth.dto.RegisterRequest;
import com.gft.auth.exception.BusinessException;
import com.gft.auth.model.Role;
import com.gft.auth.model.User;
import com.gft.auth.repository.RoleRepository;
import com.gft.auth.repository.UserRepository;
import com.gft.auth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {

        // Verifica se o usuário já existe
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new BusinessException("Usuário já existe");
        }

        // Cria usuário
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        // Mapeia roles (criando automaticamente)
        Set<Role> roles = request.roles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                .orElseGet(()-> roleRepository.save(new Role(roleName))))
                .collect(Collectors.toSet());

        user.setRoles(roles);

        // Salva usuário + roles
        userRepository.save(user);
    }

    public Map<String,String> login(LoginRequest request){
        // dispara o login do Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // pega o usuário autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = (User) authentication.getPrincipal();


        // gera token
        String token = jwtUtil.generateToken(user);

        return Map.of("token", token);


    }
}
