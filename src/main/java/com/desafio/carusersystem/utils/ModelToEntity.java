package com.desafio.carusersystem.utils;

import com.desafio.carusersystem.entity.Cars;
import com.desafio.carusersystem.entity.Usuario;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class ModelToEntity {
    public static Usuario UsuarioModelToUsuarioEntity(com.desafio.carusersystem.api.model.Usuario usuario) {
        Usuario retorno = new Usuario();
        retorno.setId(usuario.getId());
        retorno.setBirthday(LocalDate.parse(usuario.getBirthday()));
        retorno.setFirstName(usuario.getFirstName());
        retorno.setLastName(usuario.getLastName());
        retorno.setEmail(usuario.getEmail());
        retorno.setUsername(usuario.getLogin());
        retorno.setPassword(usuario.getPassword());
        retorno.setPhone(usuario.getPhone());
        if (usuario.getCars() != null) {
            retorno.setCars(usuario.getCars().stream().map(car -> {
                Cars carro =new Cars();
                carro.setColor(car.getColor());
                carro.setId(car.getId());
                carro.setLicensePlate(car.getLicensePlate());
                carro.setModel(car.getModel());
                carro.setYear(car.getYear());
                return carro;
            }).collect(Collectors.toList()));
        }
        return retorno;
    }

    public static com.desafio.carusersystem.api.model.Usuario UsuarioEntityToUsuarioModel(Usuario usuario) {
        com.desafio.carusersystem.api.model.Usuario retornar = new com.desafio.carusersystem.api.model.Usuario();
        retornar.setFirstName(usuario.getFirstName());

        return retornar;
    }
}
