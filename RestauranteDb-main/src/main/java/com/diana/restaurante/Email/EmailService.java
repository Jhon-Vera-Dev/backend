package com.diana.restaurante.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarCorreoConToken(String destinatario, String token) {
        String asunto = "Verifica tu cuenta - Restaurante Diana";
        String enlace = "http://localhost:8080/auth/confirmar?token=" + token;

        String cuerpoHtml = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f8f8f8; padding: 20px;">
                        <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 10px;">
                            <h2>¡Bienvenido a Restaurante Diana!</h2>
                            <p>Gracias por registrarte. Por favor, haz clic en el siguiente botón para activar tu cuenta:</p>
                            <a href="%s" style="display:inline-block; padding: 10px 20px; background-color: #28a745; color: white; text-decoration: none; border-radius: 5px;">Activar cuenta</a>
                            <p>Este enlace expirará en 24 horas.</p>
                        </div>
                    </body>
                </html>
                """
                .formatted(enlace);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpoHtml, true); // true: contenido HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}
