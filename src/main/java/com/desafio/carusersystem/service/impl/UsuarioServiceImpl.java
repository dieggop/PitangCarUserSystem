package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.exceptions.ExceptionNotFound;
import com.desafio.carusersystem.repository.UsuarioRepository;
import com.desafio.carusersystem.security.JwtUtil;
import com.desafio.carusersystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,UserDetailsService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() != null && usuarioRepository.findById(usuario.getId()) == null) {
            throw new ExceptionNotFound("Usuário Não encontrado");
        }
        usuario.setCreatedAt(LocalDate.now());
        usuario.setLastLogin(LocalDate.now());
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

    @Override
    public Usuario meusDados() {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader==null) {
            authorizationHeader="";
        }
        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                Usuario applicationUser = usuarioRepository.findByUsername(username);
                return applicationUser;
            } else {
                throw new ExceptionNotFound("Token inválido");
            }
        }

        return null;
    }
}
