package br.com.victorzendem.login.dto.usuario;

import jakarta.validation.constraints.Email;

public record AtualizarUsuarioParcialmenteRequest(
        String nome,
        @Email String email
) {
}
