package com.desafio.carusersystem.repository;

import com.desafio.carusersystem.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
//    Usuario findByUsername(String username);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
}
