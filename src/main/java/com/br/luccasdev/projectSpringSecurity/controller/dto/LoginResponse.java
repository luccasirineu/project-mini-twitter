package com.br.luccasdev.projectSpringSecurity.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
