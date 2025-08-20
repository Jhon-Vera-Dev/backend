package com.diana.restaurante.Auth;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String avatar; // Nuevo campo (opcional)

    // Campos de la persona
    private String dni;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;

}