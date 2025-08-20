package com.diana.restaurante.Empleado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.diana.restaurante.Empleado.model.Empleado;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByPersonaId(Long personaId);

    Optional<Empleado> findByPersonaId(Long personaId); // útil para buscar por DNI indirectamente

    boolean existsById(Long id);
}
