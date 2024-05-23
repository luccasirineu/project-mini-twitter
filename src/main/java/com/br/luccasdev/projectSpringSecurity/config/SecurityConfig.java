package com.br.luccasdev.projectSpringSecurity.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.hibernate.annotations.Immutable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.private.key")
    private RSAPrivateKey privateKey;

    @Value("${jwt.public.key")
    private RSAPublicKey publicKey;

    @Bean
    private SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { // esse metodo serve para customizar toda questão de segurança do projeto

        httpSecurity.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated()) // para dizer que qlqr request tem que ser autenticada
         .csrf(csrf -> csrf.disable()). // configuracao que é desabilitada no local mas em ambiente de produção precisa estar habilitada
                oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // para dizer que o security sera STATELESS, ou seja, nao iremos precisar guardar nada em sessao



        return httpSecurity.build();
    }

    // enconde e decode

    @Bean
    public JwtEncoder jwtEncoder(){

        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build(); //criptografando
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public JwtDecoder jwtDecoder(){

        return NimbusJwtDecoder.withPublicKey(publicKey).build(); // descriptografando a chave privada
    }

    // verificando se a senha passada pelo usuario sera a mesma senha salva no banco de dados
}
