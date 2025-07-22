package com.ikernell.backend.repository;

import com.ikernell.backend.model.Asignacion;
import com.ikernell.backend.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Integer> {
    List<Asignacion> findByProyectoIdProyecto(Integer idProyecto);

    @Query("SELECT p FROM Proyecto p")
    List<Asignacion> listarAsignacion();
}
