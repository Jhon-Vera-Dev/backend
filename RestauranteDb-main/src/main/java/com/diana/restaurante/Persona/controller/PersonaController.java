package com.diana.restaurante.Persona.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.service.PersonaService;

@RestController
@RequestMapping("/api")

public class PersonaController {
    @Autowired
    private PersonaService personaService;

    @GetMapping(value = "personas")
    public ResponseEntity<List<Persona>> listar() {
        return ResponseEntity.ok(personaService.listar());
    }

    // Obtener persona por Id
    @GetMapping(value = "personas/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable Long id) {
        Optional<Persona> persona = personaService.obtenerPorId(id);
        if (persona.isPresent()) {
            return ResponseEntity.ok(persona.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping(value = "personas")
    public ResponseEntity<?> crear(@RequestBody Persona persona) {
        if (persona.getDni() == null || !persona.getDni().matches("\\d{8}")) {
            return ResponseEntity.badRequest().body("El DNI debe tener exactamente 8 dígitos numéricos.");
        }
        if (personaService.existePorDni(persona.getDni())) {
            return ResponseEntity.badRequest().body("Ya existe una persona con ese DNI.");
        }

        return ResponseEntity.ok(personaService.guardar(persona));
    }

    // DELETE: Eliminar persona
    @DeleteMapping(value = "/personas/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Persona> personaOptional = personaService.obtenerPorId(id);
        if (personaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        personaService.eliminar(id);
        return ResponseEntity.ok().body("Persona eliminada correctamente.");
    }

    @PutMapping(value = "/personas/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        Optional<Persona> personaOptional = personaService.obtenerPorId(id);
        if (personaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Persona persona = personaOptional.get();
        if (personaActualizada.getNombres() != null) {
            persona.setNombres(personaActualizada.getNombres());
        }
        if (personaActualizada.getApellidos() != null) {
            persona.setApellidos(personaActualizada.getApellidos());
        }
        if (personaActualizada.getDni() != null) {
            persona.setDni(personaActualizada.getDni());
        }
        if (personaActualizada.getTelefono() != null) {
            persona.setTelefono(personaActualizada.getTelefono());
        }
        if (personaActualizada.getDireccion() != null) {
            persona.setDireccion(personaActualizada.getDireccion());
        }
        if (personaActualizada.getFechaNacimiento() != null) {
            persona.setFechaNacimiento(personaActualizada.getFechaNacimiento());
        }

        return ResponseEntity.ok(personaService.guardar(persona));
    }

    @PatchMapping("/personas/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Persona personaActualizada) {
        Optional<Persona> personaOptional = personaService.obtenerPorId(id);
        if (personaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Persona persona = personaOptional.get();

        if (personaActualizada.getNombres() != null)
            persona.setNombres(personaActualizada.getNombres());
        if (personaActualizada.getApellidos() != null)
            persona.setApellidos(personaActualizada.getApellidos());
        if (personaActualizada.getDni() != null)
            persona.setDni(personaActualizada.getDni());
        if (personaActualizada.getTelefono() != null)
            persona.setTelefono(personaActualizada.getTelefono());
        if (personaActualizada.getDireccion() != null)
            persona.setDireccion(personaActualizada.getDireccion());
        if (personaActualizada.getFechaNacimiento() != null)
            persona.setFechaNacimiento(personaActualizada.getFechaNacimiento());

        return ResponseEntity.ok(personaService.guardar(persona));
    }

    // GET: Obtener persona por DNI

    @GetMapping(value = "personas/dni/{dni}")
    public ResponseEntity<?> obtenerPorDni(@PathVariable String dni) {
        return personaService.obtenerPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "personas-disponibles")
    public ResponseEntity<List<Persona>> listarNoEmpleados() {
        return ResponseEntity.ok(personaService.listarNoEmpleados());
    }

}
