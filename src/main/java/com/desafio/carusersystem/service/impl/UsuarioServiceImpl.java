package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Cars;
import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.exceptions.ExceptionConflict;
import com.desafio.carusersystem.exceptions.ExceptionNotFound;
import com.desafio.carusersystem.repository.CarsRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UsuarioRepository usuarioRepository;
    private CarsRepository carsRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,UserDetailsService userDetailsService,BCryptPasswordEncoder bCryptPasswordEncoder,CarsRepository carsRepository) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = userDetailsService;
        this.carsRepository = carsRepository;
    }

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() != null && !usuarioRepository.findById(usuario.getId()).isPresent()) {
            throw new ExceptionNotFound("Usuário Não encontrado");
        }

        validacaoSaveOrUpdate(usuario);

        if (usuario.getId() == null ) {
            usuario.setCreatedAt(LocalDate.now());
            usuario.setLastLogin(LocalDate.now());
        }
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        Usuario retorno = usuarioRepository.save(usuario);
        return retorno;
    }

    private void validacaoSaveOrUpdate(Usuario usuario) throws ExceptionConflict {
        Optional<Usuario> byUsername = usuarioRepository.findByUsername(usuario.getUsername());
        Optional<Usuario> byEmail = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuario.getId() == null)
        {
            if (byUsername.isPresent()) {
                throw new ExceptionConflict("Login already exists");
            }
            if (byEmail.isPresent()) {
                throw new ExceptionConflict("Email already exists");
            }
        }
        if (usuario.getId() != null)
        {
            if (byUsername.isPresent() && byUsername.get().getId() != usuario.getId()) {
                throw new ExceptionConflict("Login already exists");
            }
            if (byEmail.isPresent() && byEmail.get().getId() != usuario.getId()) {
                throw new ExceptionConflict("Email already exists");
            }
        }

        for (Cars carroCheck : usuario.getCars()) {
            verificaExistenciaDeCarro(carroCheck);
        }

    }

    private void verificaExistenciaDeCarro(Cars carro) {
        Optional<Cars> byLicensePlate = carsRepository.findByLicensePlate(carro.getLicensePlate());
        if (byLicensePlate.isPresent()) {
            throw new ExceptionConflict("License plate already exists");
        }
    }
    @Override
    public void removeUsuario(Long id) {
        Optional<Usuario> retorno = usuarioRepository.findById(id);
        if (!retorno.isPresent()) {
            throw new ExceptionNotFound("not found");
        }
        retorno.ifPresent(usuario -> usuarioRepository.delete(usuario));

    }

    @Override
    public Usuario recuperarUsuario(Long id) {
        Optional<Usuario> retorno = usuarioRepository.findById(id);
        if (!retorno.isPresent()) {
            throw new ExceptionNotFound("not found");
        }
        return retorno.get();
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
                Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
                Usuario applicationUser = usuario.get(); //usuarioRepository.findByUsername(username);
                return applicationUser;
            } else {
                throw new ExceptionConflict("Token inválido");
            }
        }

        return null;
    }

    @Override
    public List<Usuario> listaUsuarios() {
        List<Usuario> retorno = usuarioRepository.findAll();
        return retorno;
    }
}
