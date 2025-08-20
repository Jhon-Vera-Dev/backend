package com.diana.restaurante.Empleado.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diana.restaurante.Empleado.exceptions.BadRequestException;
import com.diana.restaurante.Empleado.model.Empleado;
import com.diana.restaurante.Empleado.repository.EmpleadoRepository;
import com.diana.restaurante.Empleado.request.EmpleadoRequest;
import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.service.PersonaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final PersonaService personaService;

    @Transactional
    public Empleado registrarEmpleado(EmpleadoRequest request) {
        if (!personaService.existePorDni(request.getDni())) {
            throw new BadRequestException("La persona con el DNI proporcionado no existe.");
        }

        Persona persona = personaService.obtenerPorDni(request.getDni())
                .orElseThrow(() -> new BadRequestException("La persona con el DNI proporcionado no fue encontrada."));

        if (empleadoRepository.existsByPersonaId(persona.getId())) {
            throw new BadRequestException("La persona ya está registrada como empleado.");
        }

        Empleado empleado = new Empleado();
        empleado.setPersona(persona);
        empleado.setSalario(request.getSalario());
        empleado.setCargo(request.getCargo());
        empleado.setFechaContrato(request.getFechaContrato());
        empleado.setActivo(true);

        return empleadoRepository.save(empleado);
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    public Empleado actualizarEmpleado(Long id, EmpleadoRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        if (request.getCargo() != null)
            empleado.setCargo(request.getCargo());
        if (request.getSalario() != null)
            empleado.setSalario(request.getSalario());
        if (request.getFechaContrato() != null)
            empleado.setFechaContrato(request.getFechaContrato());

        return empleadoRepository.save(empleado);
    }

    public void eliminarPorId(Long id) {
        empleadoRepository.deleteById(id);
    }

    public List<Empleado> listarTodos() {
        return empleadoRepository.findAll();
    }
}
