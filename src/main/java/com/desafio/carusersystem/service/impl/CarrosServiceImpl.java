package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Cars;
import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.repository.CarsRepository;
import com.desafio.carusersystem.repository.UsuarioRepository;
import com.desafio.carusersystem.security.JwtUtil;
import com.desafio.carusersystem.service.CarrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CarrosServiceImpl implements CarrosService {

    private CarsRepository carsRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    private Usuario applicationUser = null;

    @Autowired
    public CarrosServiceImpl(UsuarioRepository usuarioRepository, CarsRepository carsRepository, UserDetailsService userDetailsService) {
        this.carsRepository = carsRepository;
        this.userDetailsService = userDetailsService;
        this.usuarioRepository = usuarioRepository;
    }

    private void initCarrosConfig(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            this.applicationUser = usuario.get();
        }
    }

    @Override
    public Cars saveCarro(Cars carro) {
        return null;
    }

    @Override
    public void removerCarro(Long id) {

    }

    @Override
    public Cars buscarCarro(Long id) {
        return null;
    }

    @Override
    public List<Cars> listarCarros() {
        initCarrosConfig();

        if (this.applicationUser != null ) {

        List<Cars> carros = carsRepository.findByUsuarioId(this.applicationUser.getId());
        return carros;
        }
        return null;
    }
}
