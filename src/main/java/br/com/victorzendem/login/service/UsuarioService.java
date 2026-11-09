package br.com.victorzendem.login.service;

import br.com.victorzendem.login.dto.usuario.UsuarioRequest;
import br.com.victorzendem.login.dto.usuario.UsuarioResponse;
import br.com.victorzendem.login.model.Usuario;
import br.com.victorzendem.login.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioResponse registrar(UsuarioRequest request){

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                request.senha()
        );

        usuarioRepository.save(usuario);

        return UsuarioResponse.from(usuario);
    }
    
}
