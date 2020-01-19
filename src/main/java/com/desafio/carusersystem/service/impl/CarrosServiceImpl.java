package com.desafio.carusersystem.service.impl;

import com.desafio.carusersystem.entity.Cars;
import com.desafio.carusersystem.entity.Usuario;
import com.desafio.carusersystem.exceptions.ExceptionConflict;
import com.desafio.carusersystem.exceptions.ExceptionNotFound;
import com.desafio.carusersystem.exceptions.ExceptionUnauthorized;
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

    private void initCarrosConfig() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            this.applicationUser = usuario.get();
        }
    }

    @Override
    public Cars saveCarro(Cars carro) {
        if (carro.getId() != null && !carsRepository.findByIdAndUsuarioId(carro.getId(),this.applicationUser.getId()).isPresent()) {
            throw new ExceptionNotFound("Not Found");
        }
        validacaoSaveOrUpdate(carro);
        carro.setUsuario(this.applicationUser);
        Cars retorno = carsRepository.save(carro);
        return retorno;
    }

    private void validacaoSaveOrUpdate(Cars carro) throws ExceptionConflict {
        Optional<Cars> byLicensePlate = carsRepository.findByLicensePlate(carro.getLicensePlate());

        if (carro.getId() == null)
        {
            if (byLicensePlate.isPresent()) {
                throw new ExceptionConflict("License plate already exists");
            }

        }
        if (carro.getId() != null)
        {
            if (byLicensePlate.isPresent() && byLicensePlate.get().getId() != carro.getId()) {
                throw new ExceptionConflict("License plate already exists");
            }
        }
    }

    @Override
    public void removerCarro(Long id) {
        initCarrosConfig();
        if (this.applicationUser != null) {
            Optional<Cars> carro = carsRepository.findById(id);

            if (!carro.isPresent()) {
                throw new ExceptionNotFound("Not Found");
            }

            if (carro.isPresent()) {
                if (carro.get().getUsuario().getId() != this.applicationUser.getId()) {
                    throw new ExceptionConflict("Unauthorized");
                }
            }

            carro.ifPresent(cars -> carsRepository.delete(cars));

        }
    }

    @Override
    public Cars buscarCarro(Long id) {
        initCarrosConfig();
        if (this.applicationUser != null) {
            Optional<Cars> carro = carsRepository.findById(id);

            if (!carro.isPresent()) {
                throw new ExceptionNotFound("Not Found");
            }

            if (carro.isPresent()) {
                if (carro.get().getUsuario().getId() != this.applicationUser.getId()) {
                    throw new ExceptionUnauthorized("Unauthorized");
                }
            }

            Cars retorno = carro.get();
            retorno.setCounter(retorno.getCounter()+1);
            carsRepository.save(retorno);
            return retorno;
        }
        return null;
    }

    @Override
    public List<Cars> listarCarros() {
        initCarrosConfig();

        if (this.applicationUser != null) {
            List<Cars> carros = carsRepository.findByUsuarioId(this.applicationUser.getId());
            return carros;
        } else {
            throw new ExceptionConflict("Unauthorized");
        }
    }
}
