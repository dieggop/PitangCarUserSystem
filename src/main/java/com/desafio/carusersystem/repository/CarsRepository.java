package com.desafio.carusersystem.repository;

import com.desafio.carusersystem.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarsRepository extends JpaRepository<Cars, Long> {
    List<Cars> findByUsuarioId(Long id);
    Optional<Cars> findByLicensePlate(String license);
    Optional<Cars> findByIdAndUsuarioId(Long id, Long usuarioId);
}
