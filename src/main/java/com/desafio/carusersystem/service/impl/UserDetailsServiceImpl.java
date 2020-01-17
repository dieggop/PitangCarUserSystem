package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        Usuario applicationUser = usuario.get();
        if (applicationUser == null) {
            throw new UsernameNotFoundException("“Invalid login or password");
        }
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), true, true, true, true,  emptyList());
    }
}
