package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.CarsApi;
import com.desafio.carusersystem.api.model.Cars;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CarController implements CarsApi {

    @Override
    public ResponseEntity<Void> atualizaCarro(Long id, @Valid Cars body) {
        return null;
    }

    @Override
    public ResponseEntity<Void> cadastrarCarro(@Valid Cars body) {
        return null;
    }

    @Override
    public ResponseEntity<Cars> listarCarros() {
        return null;
    }

    @Override
    public ResponseEntity<Cars> recuperaCarro(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> removeCarro(Long id) {
        return null;
    }
}
