package com.desafio.carusersystem.service;
import com.desafio.carusersystem.entity.Usuario;

public interface UsuarioService {

    Usuario save(Usuario usuario);
    void removeUsuario(Long id);
    Usuario recuperarUsuario(Long id);
    Usuario meusDados();
}
