package com.diana.restaurante.Saludo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SaludoController {
    @PostMapping(value = "saludo")
    public String Saludo() {
        return "hola desde saludo";
    }

}
