package br.com.victorzendem.login.controller;

import br.com.victorzendem.login.dto.usuario.*;
import br.com.victorzendem.login.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping()
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid UsuarioRequest request){
        UsuarioResponse response = usuarioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioRequest request){
        return ResponseEntity.ok(usuarioService.atualizarDados(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizarParcialmente(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioParcialmenteRequest request){
        return ResponseEntity.ok(usuarioService.atualizarParcialmente(id, request));
    }

    @PatchMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody @Valid AlterarSenhaRequest request){
        usuarioService.alterarSenha(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Void> ativar(@PathVariable Long id){
        usuarioService.ativar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Void> desativar(@PathVariable Long id){
        usuarioService.desativar(id);
        return ResponseEntity.noContent().build();
    }



}
