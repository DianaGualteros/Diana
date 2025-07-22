package com.ikernell.backend.service;

import com.ikernell.backend.model.Proyecto;
import com.ikernell.backend.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    public Proyecto crearProyecto(Proyecto proyecto) {
        if (proyecto.getFechaFinEstimada().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha estimada no puede ser anterior a la fecha actual");
        }

        proyecto.setEstado(Proyecto.EstadoProyecto.Pendiente); // fuerza estado inicial
        return proyectoRepository.save(proyecto);
    }

    public Proyecto cambiarEstado(Integer id, Proyecto.EstadoProyecto nuevoEstado) {
        Proyecto proyecto = proyectoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        proyecto.setEstado(nuevoEstado);
        return proyectoRepository.save(proyecto);
    }
}
