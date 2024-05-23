package com.br.luccasdev.projectSpringSecurity.controller;

import com.br.luccasdev.projectSpringSecurity.controller.dto.LoginRequest;
import com.br.luccasdev.projectSpringSecurity.controller.dto.LoginResponse;
import com.br.luccasdev.projectSpringSecurity.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    private JwtEncoder jwtEncoder;
    private UserRepository userRepository;

    public TokenController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var user = userRepository.findByUsername(loginRequest.username());

        if(user.isEmpty()){
            throw new BadCredentialsException("user or password is invalid");
        }

    }
}
