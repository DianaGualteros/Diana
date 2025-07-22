package com.ikernell.backend.service;

import com.ikernell.backend.dto.LoginRequest;
import com.ikernell.backend.model.Usuario;
import com.ikernell.backend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepo;

    public AuthService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public ResponseEntity<?> login(LoginRequest req) {
        Optional<Usuario> optUser = usuarioRepo.findByCorreo(req.getCorreo());

        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Usuario no encontrado");
        }

        Usuario user = optUser.get();

        if (!user.getPassword().equals(req.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Contraseña incorrecta");
        }

        if (user.getEstadoRol() == Usuario.EstadoRol.Inactivo) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Usuario inactivo");
        }

        return ResponseEntity.ok(user);
    }
}
