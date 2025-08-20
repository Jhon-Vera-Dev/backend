package com.diana.restaurante.Empleado.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmpleadoRequest {
    private String dni; // para buscar o registrar persona
    private String cargo; // requerido
    private LocalDate fechaContrato; // opcional pero recomendable
    private BigDecimal salario; // requerido
}
