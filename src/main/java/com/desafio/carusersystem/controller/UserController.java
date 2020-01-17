package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.UsersApi;
import com.desafio.carusersystem.api.model.Usuario;
import com.desafio.carusersystem.api.model.UsuarioAtualiza;
import com.desafio.carusersystem.api.model.UsuarioResponse;
import com.desafio.carusersystem.exceptions.BlankFields;
import com.desafio.carusersystem.exceptions.ExceptionConflict;
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

import javax.validation.Valid;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController implements UsersApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;




    @Override
    public ResponseEntity<Void> atualizaUsuario(Long id, @Valid Usuario body) {

        if (id != body.getId()) {
            return new ResponseEntity(new Message("Invalid fields", HttpStatus.CONFLICT), HttpStatus.BAD_REQUEST);
        }

        try {
            validarUsuario(body);
            usuarioService.save(ModelToEntity.UsuarioModelToUsuarioEntity(body));
        } catch (BlankFields e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch(ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> cadastrarUsuario(@Valid Usuario body) {
        try {
            validarUsuario(body);
            System.out.println(ModelToEntity.UsuarioModelToUsuarioEntity(body));
            usuarioService.save(ModelToEntity.UsuarioModelToUsuarioEntity(body));
        } catch (BlankFields e) {

            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }catch(ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UsuarioResponse> listarUsuarios() {
        UsuarioResponse retorno = new UsuarioResponse();
        retorno.setUsuarios(ModelToEntity.listUsuarioEntityToListUsuarioModel(usuarioService.listaUsuarios()));
        return new ResponseEntity<>(retorno, HttpStatus.OK);
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
