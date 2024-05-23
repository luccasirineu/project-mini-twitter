package com.br.luccasdev.projectSpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    private SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // esse metodo serve para customizar toda questão de segurança do projeto

        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // para dizer que qlqr request tem que ser autenticada
         .csrf(csrf -> csrf.disable()). // configuracao que é desabilitada no local mas em ambiente de produção precisa estar habilitada
                oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // para dizer que o security sera STATELESS, ou seja, nao iremos precisar guardar nada em sessao



        return httpSecurity.build();
    }
}
