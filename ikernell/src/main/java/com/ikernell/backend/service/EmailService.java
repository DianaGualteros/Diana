package com.ikernell.backend.service;

import com.ikernell.backend.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    public void enviarCorreoBienvenida(Usuario usuario) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(usuario.getCorreo());
        mensaje.setSubject("¡Bienvenido a la Plataforma Ikernell!");

        String cuerpo = String.format(
            "Hola %s,\n\n" +
                "Tu cuenta ha sido registrada exitosamente.\n\n" +
                "Tus credenciales de acceso son:\n" +
                "Correo: %s\n" +
                "Contraseña: %s\n\n" +
                "Puedes cambiar tu contraseña después de iniciar sesión.\n\n" +
                "Para establecer tu contraseña inicial, haz clic en el siguiente enlace:\n" +
                "http://localhost:3002/establecer_password?token=%s\n\n" +
                "Este enlace expira en 24 horas.\n\n" +
                "Saludos,\nEquipo Ikernell",
            usuario.getPrimerNombre(),
            usuario.getCorreo(),
            usuario.getPassword(),
            usuario.getToken()
        );

        mensaje.setText(cuerpo);
        mensaje.setFrom(env.getProperty("spring.mail.username")); // ← Línea clave
        mailSender.send(mensaje);
    }
}
