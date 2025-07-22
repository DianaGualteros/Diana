package com.ikernell.backend.repository;

import com.ikernell.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByTipoIdentificacionAndIdentificacion(Usuario.TipoIdentificacion tipo, Integer identificacion);
    Optional<Usuario> findByToken(String token);
    boolean existsByCorreo(String correo);

    boolean existsByTipoIdentificacionAndIdentificacion(Usuario.TipoIdentificacion tipo, Integer identificacion);

    List<Usuario> findByRol(Usuario.Rol rol);
    @Query("SELECT u FROM Usuario u WHERE u.rol = 'Lider' AND u.estadoRol = 'Activo'")
    List<Usuario> obtenerLideresActivosJPQL();

    @Query("SELECT u FROM Usuario u WHERE u.rol = 'Desarrollador' AND u.estadoRol = 'Activo'")
    List<Usuario> obtenerDesarrolladoresActivosJPQL();

}
