package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.MeApi;
import com.desafio.carusersystem.api.model.UsuarioMe;
import com.desafio.carusersystem.exceptions.Message;
import com.desafio.carusersystem.security.JwtUtil;
import com.desafio.carusersystem.service.UsuarioService;
import com.desafio.carusersystem.utils.ModelToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MeController implements MeApi {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;


    @Override
    public ResponseEntity<UsuarioMe> dadosUsuario() {


        try {

            return new ResponseEntity(ModelToEntity.UsuarioEntityToUsuarioModel(usuarioService.meusDados()), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);

        }

    }

}
