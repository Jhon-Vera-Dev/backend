package com.diana.restaurante.Empleado.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.diana.restaurante.Empleado.model.Empleado;
import com.diana.restaurante.Empleado.request.EmpleadoRequest;
import com.diana.restaurante.Empleado.service.EmpleadoService;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping("/empleado")
    public ResponseEntity<String> registrarEmpleado(@RequestBody EmpleadoRequest request) {
        try {
            empleadoService.registrarEmpleado(request);
            return ResponseEntity.ok("Empleado registrado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/empleado/{id}")
    public ResponseEntity<?> obtenerEmpleadoPorId(@PathVariable Long id) {
        Optional<Empleado> empleadoOpt = empleadoService.obtenerPorId(id);
        if (empleadoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleadoOpt.get());
    }

    @PutMapping("/empleado/{id}")
    public ResponseEntity<?> actualizarEmpleado(@PathVariable Long id, @RequestBody EmpleadoRequest request) {
        try {
            Empleado actualizado = empleadoService.actualizarEmpleado(id, request);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        if (empleadoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        empleadoService.eliminarPorId(id);
        return ResponseEntity.ok("Empleado eliminado correctamente.");
    }

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarTodosLosEmpleados() {
        return ResponseEntity.ok(empleadoService.listarTodos());
    }

}
