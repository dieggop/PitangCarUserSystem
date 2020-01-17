package com.desafio.carusersystem.utils;

import com.desafio.carusersystem.entity.Cars;
import com.desafio.carusersystem.entity.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    public static List<com.desafio.carusersystem.api.model.Usuario> listUsuarioEntityToListUsuarioModel(List<Usuario> usuarios) {
    if (!usuarios.isEmpty()) {
    List<com.desafio.carusersystem.api.model.Usuario> retorno = new ArrayList<>();
        for(Usuario usuario : usuarios) {
            retorno.add(UsuarioEntityToUsuarioModel(usuario));
        }
//        return usuarios.stream().map(usuario -> UsuarioEntityToUsuarioModel(usuario)).collect(Collectors.toList());
        return retorno;
    }
        return null;
    }
    public static com.desafio.carusersystem.api.model.Usuario UsuarioEntityToUsuarioModel(Usuario usuario) {
        com.desafio.carusersystem.api.model.Usuario retorno = new com.desafio.carusersystem.api.model.Usuario();
        retorno.setId(usuario.getId());
        retorno.setBirthday(usuario.getBirthday().toString());
        retorno.setFirstName(usuario.getFirstName());
        retorno.setLastName(usuario.getLastName());
        retorno.setEmail(usuario.getEmail());
        retorno.setLogin(usuario.getUsername());
        retorno.setPhone(usuario.getPhone());
        if (usuario.getCreatedAt() != null) {
            retorno.createdAt(usuario.getCreatedAt().toString());
        }
        if (usuario.getLastLogin() != null) {
            retorno.setLastLogin(usuario.getLastLogin().toString());
        }
        retorno.setCars(usuario.getCars().stream().map(car -> {
            com.desafio.carusersystem.api.model.Cars carro =new com.desafio.carusersystem.api.model.Cars();
            carro.setColor(car.getColor());
            carro.setId(car.getId());
            carro.setLicensePlate(car.getLicensePlate());
            carro.setModel(car.getModel());
            carro.setYear(car.getYear());
            carro.setCount(car.getCounter());
            return carro;
        }).collect(Collectors.toList()));

        return retorno;
    }
}
