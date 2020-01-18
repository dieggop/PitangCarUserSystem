package com.desafio.carusersystem.repository;

import com.desafio.carusersystem.entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarsRepository extends JpaRepository<Cars, Long> {
    List<Cars> findByUsuarioId(Long id);
}
