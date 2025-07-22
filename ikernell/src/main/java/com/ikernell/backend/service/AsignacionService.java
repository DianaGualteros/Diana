package com.ikernell.backend.service;

import com.ikernell.backend.model.Asignacion;
import com.ikernell.backend.model.Usuario;
import com.ikernell.backend.repository.AsignacionRepository;
import com.ikernell.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Asignacion asignarLider(Asignacion asignacion) {
        validarUsuario(asignacion);
        asignacion.setFechaAsignacion(LocalDate.now());
        asignacion.setEstado(Asignacion.Estado.ACTIVO);
        return asignacionRepository.save(asignacion);
    }
    public Asignacion asignarDesarrollador(Asignacion asignacion) {
        validarUsuario(asignacion);
        asignacion.setFechaAsignacion(LocalDate.now());
        asignacion.setEstado(Asignacion.Estado.ACTIVO);
        return asignacionRepository.save(asignacion);
    }

    public List<Asignacion> getPorProyecto(Integer idProyecto) {
        return asignacionRepository.findByProyectoIdProyecto(idProyecto);
    }

    private void validarUsuario(Asignacion asignacion) {
        if (asignacion.getUsuario() == null || asignacion.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("Usuario o ID de usuario no proporcionado");
        }





    }




}
