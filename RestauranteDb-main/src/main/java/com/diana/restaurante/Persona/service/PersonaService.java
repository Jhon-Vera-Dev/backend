package com.diana.restaurante.Persona.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diana.restaurante.Empleado.repository.EmpleadoRepository;
import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.repository.PersonalRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonaService {

    @Autowired
    private PersonalRepository personaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository; // ✅ inyectamos correctamente el repositorio de empleados

    public List<Persona> listar() {
        return personaRepository.findAll();
    }

    public Optional<Persona> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }

    public boolean existePorDni(String dni) {
        return personaRepository.findByDni(dni).isPresent();
    }

    public Optional<Persona> obtenerPorDni(String dni) {
        return personaRepository.findByDni(dni);
    }

    public List<Persona> listarNoEmpleados() {
        List<Persona> todas = personaRepository.findAll();
        List<Long> idsEmpleados = empleadoRepository.findAll()
                .stream()
                .map(e -> e.getPersona().getId())
                .collect(Collectors.toList());

        return todas.stream()
                .filter(p -> !idsEmpleados.contains(p.getId()))
                .collect(Collectors.toList()); // o simplemente .toList() si usas Java 16+
    }
}