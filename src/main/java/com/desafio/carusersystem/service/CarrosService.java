package com.desafio.carusersystem.service;

import com.desafio.carusersystem.entity.Cars;

import java.util.List;

public interface CarrosService {

    Cars saveCarro(Cars carro);
    void removerCarro(Long id);
    Cars buscarCarro(Long id);
    List<Cars> listarCarros();

}
