package com.ikernell.backend.controller;

import com.ikernell.backend.model.Asignacion;
import com.ikernell.backend.model.Proyecto;
import com.ikernell.backend.model.Usuario;
import com.ikernell.backend.repository.AsignacionRepository;
import com.ikernell.backend.repository.UsuarioRepository;
import com.ikernell.backend.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;
    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Asignacion> crearAsignacion(@RequestBody Asignacion asignacion) {
        return ResponseEntity.ok(asignacionService.asignarLider(asignacion));
    }

    @GetMapping("/proyecto/{id}")
    public ResponseEntity<List<Asignacion>> listarPorProyecto(@PathVariable Integer id) {
        return ResponseEntity.ok(asignacionService.getPorProyecto(id));
    }

    @GetMapping
    public ResponseEntity<List<Asignacion>> listarAsignacion() {
        return ResponseEntity.ok(asignacionRepository.findAll());
    }

    @PostMapping("/desarrollador")
    public ResponseEntity<?> asignarDesarrollador(@RequestBody Asignacion asignacion, @RequestHeader("idUsuario") Integer idUsuario) {
        try {
            Usuario lider = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new IllegalArgumentException("Líder no encontrado"));

            asignacion.setAsignadoPor(lider);
            asignacion.setFechaAsignacion(LocalDate.now());
            asignacion.setEstado(Asignacion.Estado.ACTIVO);

            Asignacion nuevaAsignacion = asignacionService.asignarDesarrollador(asignacion);
            return ResponseEntity.ok(nuevaAsignacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
