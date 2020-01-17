package com.desafio.carusersystem.controller;

import com.auth0.jwt.JWT;
import com.desafio.carusersystem.api.MeApi;
import com.desafio.carusersystem.api.UsersApi;
import com.desafio.carusersystem.api.model.*;
import com.desafio.carusersystem.exceptions.BlankFields;
import com.desafio.carusersystem.exceptions.Message;
import com.desafio.carusersystem.security.JwtUtil;
import com.desafio.carusersystem.service.UsuarioService;
import com.desafio.carusersystem.utils.ModelToEntity;
import io.swagger.annotations.ApiParam;
import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.desafio.carusersystem.utils.SecurityConstant.EXPIRATION_TIME;
import static com.desafio.carusersystem.utils.SecurityConstant.SECRET;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserCarController implements UsersApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;




    @Override
    public ResponseEntity<Void> atualizaUsuario(Long id, @Valid UsuarioAtualiza body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> cadastrarUsuario(@Valid Usuario body) {
        try {
            validarUsuario(body);
            System.out.println(ModelToEntity.UsuarioModelToUsuarioEntity(body));
            usuarioService.save(ModelToEntity.UsuarioModelToUsuarioEntity(body));
        } catch (BlankFields e) {

            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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


    private void validarUsuario(Usuario body ) {
        if (body.getFirstName().isEmpty() || body.getBirthday().isEmpty() || body.getEmail().isEmpty() || body.getLastName().isEmpty() || body.getLogin().isEmpty() || body.getPassword().isEmpty() || body.getPhone().isEmpty()) throw new BlankFields();
    }

}
