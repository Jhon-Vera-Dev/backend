package com.diana.restaurante.Auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diana.restaurante.Jwt.JwtService;
import com.diana.restaurante.Persona.model.Persona;
import com.diana.restaurante.Persona.repository.PersonalRepository;
import com.diana.restaurante.User.Role;
import com.diana.restaurante.User.User;
import com.diana.restaurante.User.UserRepository;
import com.diana.restaurante.Email.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
        private final PersonalRepository personalRepository;
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final EmailService emailService;

        public AuthResponse register(RegisterRequest request) {
                System.out.println("🔧 Iniciando registro de usuario con DNI: " + request.getDni());

                // Verificar si la persona ya existe
                Persona persona = personalRepository.findByDni(request.getDni()).orElse(null);

                if (persona == null) {
                        // Crear nueva persona si no existe
                        persona = Persona.builder()
                                        .dni(request.getDni())
                                        .nombres(request.getNombres())
                                        .apellidos(request.getApellidos())
                                        .telefono(request.getTelefono())
                                        .direccion(request.getDireccion())
                                        .fechaNacimiento(request.getFechaNacimiento())
                                        .estado(true)
                                        .build();

                        persona = personalRepository.save(persona); // Guardar y obtener con ID
                        System.out.println("🧍 Persona creada con ID: " + persona.getId());
                }

                // Preparar valores del usuario
                String verificationToken = UUID.randomUUID().toString();
                LocalDateTime expiration = LocalDateTime.now().plusHours(24);

                User user = User.builder()
                                .username(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .email(request.getEmail())
                                .avatar(request.getAvatar())
                                .persona(persona)
                                .rol(Role.MESERO) // O el que uses
                                .estado(false)
                                .tokenVerificacion(verificationToken)
                                .expiracionToken(expiration)
                                .build();

                System.out.println("✅ Usuario construido correctamente: " + user.getUsername());

                userRepository.save(user);

                System.out.println("📧 Enviando correo de verificación a: " + user.getEmail());
                emailService.enviarCorreoConToken(user.getEmail(), verificationToken);

                return AuthResponse.builder()
                                .token("Cuenta registrada, revisa tu correo para activar.")
                                .build();
        }

        public String confirmarCuenta(String token) {
                User user = userRepository.findByTokenVerificacion(token)
                                .orElseThrow(() -> new RuntimeException("Token no válido"));

                if (user.getExpiracionToken().isBefore(LocalDateTime.now())) {
                        return "El token ha expirado.";
                }

                user.setEstado(true);
                user.setTokenVerificacion(null);
                user.setExpiracionToken(null);
                userRepository.save(user);

                return "Cuenta activada exitosamente.";
        }

        public AuthResponse login(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUsername(),
                                                request.getPassword()));

                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                if (!Boolean.TRUE.equals(user.getEstado())) {
                        throw new RuntimeException("Cuenta no activada. Por favor, revisa tu correo.");
                }

                String token = jwtService.getToken(user);
                Persona persona = user.getPersona();
                return AuthResponse.builder()
                                .token(token)
                                .rol(user.getRol().name())
                                .nombres(persona.getNombres())
                                .apellidos(persona.getApellidos())
                                .build();
        }

}
