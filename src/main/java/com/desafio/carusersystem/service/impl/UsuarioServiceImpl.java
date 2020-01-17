package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.exceptions.ExceptionNotFound;
import com.desafio.carusersystem.repository.UsuarioRepository;
import com.desafio.carusersystem.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() != null && usuarioRepository.findById(usuario.getId()) == null) {
            throw new ExceptionNotFound("Usuário Não encontrado");
        }
        usuario.setCreatedAt(LocalDate.now());
        usuario.setLastLogin(LocalDate.now());
        usuario.setCounter(new Long(0));
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        usuarioRepository.save(usuario);
        return usuario;
    }

    @Override
    public void removeUsuario(Long id) {

    }

    @Override
    public Usuario recuperarUsuario(Long id) {
        return null;
    }
}
