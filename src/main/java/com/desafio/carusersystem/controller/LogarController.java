package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.SigninApi;
import com.desafio.carusersystem.api.model.UserLogin;
import com.desafio.carusersystem.api.model.UsuarioLoginResponse;
import com.desafio.carusersystem.exceptions.Message;
import com.desafio.carusersystem.security.JwtUtil;
import com.desafio.carusersystem.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LogarController implements SigninApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;




    @Override
    public ResponseEntity<UsuarioLoginResponse> logarUsuario(@Valid UserLogin body) {
        System.out.print(body);
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.getLogin(), body.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(body.getLogin());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            return new ResponseEntity<>(new UsuarioLoginResponse().token(jwt), HttpStatus.OK);
        }
        catch(BadCredentialsException e ) {
            return new ResponseEntity(new Message("Invalid login or password",HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        } catch(AuthenticationServiceException e) {
            return new ResponseEntity(new Message("Invalid login or password",HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

}
