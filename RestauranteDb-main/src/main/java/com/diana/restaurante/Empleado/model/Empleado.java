package com.diana.restaurante.Empleado.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.diana.restaurante.Persona.model.Persona;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id", nullable = false)
    private Persona persona;

    @Column(nullable = false)
    private String cargo;

    @Column(name = "fecha_contrato")
    private LocalDate fechaContrato;

    @Column(nullable = false)
    private BigDecimal salario;

    @Column(nullable = false)
    private boolean activo = true;

    // Método útil si quieres acceder al DNI directamente desde Empleado
    public String getDni() {
        return persona != null ? persona.getDni() : null;
    }
}
