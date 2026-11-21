package br.com.victorzendem.login.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AtualizarUsuarioRequest(
        @NotBlank String nome,
        @NotBlank @Email String email
) {
}
