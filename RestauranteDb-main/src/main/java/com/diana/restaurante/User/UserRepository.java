package com.diana.restaurante.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByTokenVerificacion(String token);

    Optional<User> findByUsername(String username);

}