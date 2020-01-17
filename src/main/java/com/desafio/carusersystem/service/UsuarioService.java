package com.desafio.carusersystem.service;
import com.desafio.carusersystem.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario save(Usuario usuario);
    void removeUsuario(Long id);
    Usuario recuperarUsuario(Long id);
    List<Usuario> listaUsuarios();
    Usuario meusDados();
}
