package br.com.victorzendem.login.service;

import br.com.victorzendem.login.dto.usuario.*;
import br.com.victorzendem.login.exceptions.SenhaIncorretaException;
import br.com.victorzendem.login.exceptions.UsuarioNaoEncontradoException;
import br.com.victorzendem.login.model.Usuario;
import br.com.victorzendem.login.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioResponse registrar(UsuarioRequest request){

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                request.senha()
        );

        usuarioRepository.save(usuario);

        return UsuarioResponse.from(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarDados(Long id, AtualizarUsuarioRequest request){

        Usuario usuario = buscarPorId(id);
        usuario.atualizarDados(request.nome(), request.email());
        return UsuarioResponse.from(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarParcialmente(Long id, AtualizarUsuarioParcialmenteRequest request){

        Usuario usuario = buscarPorId(id);
        usuario.atualizarParcialmente(request.nome(), request.email());
        return UsuarioResponse.from(usuario);
    }

    private Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(UsuarioNaoEncontradoException::new);
    }

    public void alterarSenha(Long id, AlterarSenhaRequest request){

        Usuario usuario = buscarPorId(id);
        if(passwordEncoder.matches(request.senhaAtual(), usuario.getSenha())){
            throw new SenhaIncorretaException();
        }
        usuario.alterarSenha(passwordEncoder.encode(request.senhaNova()));
    }

    public void deletar(Long id){
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    public void ativar(Long id){
        Usuario usuario = buscarPorId(id);
        usuario.ativar();
    }

    public void desativar(Long id){
        Usuario usuario = buscarPorId(id);
        usuario.desativar();
    }
}
