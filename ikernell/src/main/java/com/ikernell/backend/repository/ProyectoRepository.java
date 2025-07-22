package com.ikernell.backend.repository;

import com.ikernell.backend.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Integer> {


        @Query("SELECT p FROM Proyecto p")
        List<Proyecto> listarProyectos();
    }



