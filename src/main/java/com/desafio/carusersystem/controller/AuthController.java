package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.ApiApi;
import com.desafio.carusersystem.api.model.*;
import com.desafio.carusersystem.exceptions.Message;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController implements ApiApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<UsuarioLoginResponse> logarUsuario(@Valid UserLogin body) {
        System.out.print(body);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(new UsuarioLoginResponse(), HttpStatus.OK);
        }
        catch(BadCredentialsException e ) {
            return new ResponseEntity(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> atualizaUsuario(Long id, @Valid UsuarioAtualiza body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> cadastrarUsuario(@Valid Usuario body) {
        return null;
    }

    @Override
    public ResponseEntity<UsuarioResponse> listarUsuarios() {
        return null;
    }

    @Override
    public ResponseEntity<Usuario> recuperaUsuario(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeUsuario(Long id) {
        return null;
    }
}
