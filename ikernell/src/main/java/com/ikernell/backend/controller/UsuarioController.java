package com.ikernell.backend.controller;

import com.ikernell.backend.dto.EstablecerPasswordRequest;
import com.ikernell.backend.model.Usuario;
import com.ikernell.backend.repository.UsuarioRepository;
import com.ikernell.backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;


    private static final String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(
        @RequestPart("usuario") Usuario usuario,
        @RequestPart("foto") MultipartFile foto) {

        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            return ResponseEntity.badRequest().body("El correo ya está registrado.");
        }

        if (usuarioRepository.existsByTipoIdentificacionAndIdentificacion(
            usuario.getTipoIdentificacion(), usuario.getIdentificacion())) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese tipo de identificación y número.");
        }

        if (usuario.getFechaNacimiento() == null || LocalDate.now().minusYears(18).isBefore(usuario.getFechaNacimiento())) {
            return ResponseEntity.badRequest().body("El usuario debe tener al menos 18 años.");
        }

        try {
            File uploadsDir = new File(UPLOAD_DIR);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs();
            }

            String nombreArchivo = StringUtils.cleanPath(foto.getOriginalFilename());
            Path rutaDestino = Paths.get(UPLOAD_DIR + nombreArchivo);
            foto.transferTo(rutaDestino.toFile());
            usuario.setFoto(nombreArchivo);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar la foto: " + e.getMessage());
        }


        if (usuario.getEstadoRol() == null) {
            usuario.setEstadoRol(Usuario.EstadoRol.Activo);
        }

        String token = usuarioService.generarToken();
        usuario.setToken(token);
        usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok("Usuario registrado correctamente y correo enviado.");
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDatosUsuario(@PathVariable Integer id, @RequestBody Usuario datosActualizados) {
        try {
            Usuario actualizado = usuarioService.actualizarDatosPermitidos(id, datosActualizados);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

        @PutMapping("/estado/{id}")
    public ResponseEntity<?> cambiarEstado(@PathVariable Integer id, @RequestParam Usuario.EstadoRol nuevoEstado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setEstadoRol(nuevoEstado);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Estado actualizado a " + nuevoEstado);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/establecer-password")
    public void restablecerPassword(@RequestBody EstablecerPasswordRequest request) {
        System.out.println("Token recibido: " + request.getToken());
        System.out.println("Contraseña nueva recibida: " + request.getNuevaPassword());

        Usuario usuario = usuarioRepository.findByToken(request.getToken())
            .orElseThrow(() -> new RuntimeException("Token inválido o ya ha sido usado."));

        if (request.getNuevaPassword() == null || request.getNuevaPassword().isEmpty()) {
            throw new RuntimeException("La nueva contraseña no puede estar vacía.");
        }

        usuario.setPassword(request.getNuevaPassword().trim());
        usuario.setToken(null);
        usuarioRepository.save(usuario);
    }
    @GetMapping("/lideres")
    public ResponseEntity<List<Usuario>> obtenerLideresActivos() {
        return ResponseEntity.ok(usuarioService.obtenerLideresActivos());
    }

    @GetMapping("/desarrolladores")
    public ResponseEntity<List<Usuario>> obtenerDesarrolladoresActivos() {
        return ResponseEntity.ok(usuarioService.obtenerDesarrolladoresActivos());
    }





}
