package br.com.victorzendem.login.dto.usuario;

import br.com.victorzendem.login.enums.StatusUsuario;
import br.com.victorzendem.login.enums.TipoUsuario;
import br.com.victorzendem.login.model.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        StatusUsuario status,
        TipoUsuario tipoUsuario,
        LocalDateTime createin,
        LocalDateTime updateIn
) {

    public static UsuarioResponse from(Usuario usuario){

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getStatus(),
                usuario.getTipoUsuario(),
                usuario.getCreateIn(),
                usuario.getUpdateIn()
        );
    }
}
