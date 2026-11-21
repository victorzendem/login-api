package br.com.victorzendem.login.model;


import br.com.victorzendem.login.enums.StatusUsuario;
import br.com.victorzendem.login.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusUsuario status = StatusUsuario.ATIVO;

    @Enumerated(EnumType.STRING)
    @Column(name = "perfil", nullable = false)
    private TipoUsuario tipoUsuario = TipoUsuario.ROLE_USER;


    @CreationTimestamp
    @Column(name = "created_in", updatable = false)
    private LocalDateTime createIn;


    @UpdateTimestamp
    @Column(name = "updated_in", nullable = false)
    private LocalDateTime updateIn;


    public Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public void atualizarDados(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public void atualizarParcialmente(String nome, String email){
        if(nome != null && !nome.isBlank()){
            this.nome = nome;
        }

        if(email != null && !email.isBlank()){
            this.email = email;
        }
    }

    public void alterarSenha(String novaSenhaCriptografada){
        this.senha = novaSenhaCriptografada;
    }


    public void ativar(){
        this.status = StatusUsuario.ATIVO;
    }

    public void desativar(){
        this.status = StatusUsuario.INATIVO;
    }

}
