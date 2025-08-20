package com.diana.restaurante.Persona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diana.restaurante.Persona.model.Persona;

@Repository
public interface PersonalRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByDni(String dni); // Método para buscar por DNI
}