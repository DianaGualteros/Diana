package com.ikernell.backend.service;

import com.ikernell.backend.model.Usuario;
import com.ikernell.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ikernell.backend.service.TokenService;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    public void registrarUsuario(Usuario usuario) {

        String token = tokenService.generarToken();


        usuario.setToken(token);


        usuarioRepository.save(usuario);


        emailService.enviarCorreoBienvenida(usuario);
    }


    public String generarToken() {
        return tokenService.generarToken();
    }
    public Usuario actualizarDatosPermitidos(Integer id, Usuario datosActualizados) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarioExistente.setPrimerNombre(datosActualizados.getPrimerNombre());
        usuarioExistente.setSegundoNombre(datosActualizados.getSegundoNombre());
        usuarioExistente.setPrimerApellido(datosActualizados.getPrimerApellido());
        usuarioExistente.setSegundoApellido(datosActualizados.getSegundoApellido());
        usuarioExistente.setCorreo(datosActualizados.getCorreo());
        usuarioExistente.setRol(datosActualizados.getRol());
        usuarioExistente.setProfesion(datosActualizados.getProfesion());
        usuarioExistente.setEspecialidad(datosActualizados.getEspecialidad());
        usuarioExistente.setDireccion(datosActualizados.getDireccion());

        return usuarioRepository.save(usuarioExistente);
    }
    public List<Usuario> obtenerLideresActivos() {
        return usuarioRepository.obtenerLideresActivosJPQL();
    }
    public List<Usuario> obtenerDesarrolladoresActivos() {
        return usuarioRepository.obtenerDesarrolladoresActivosJPQL();
    }






}
