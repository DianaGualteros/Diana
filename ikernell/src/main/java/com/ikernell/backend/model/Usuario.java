package com.ikernell.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name = "usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"correo"}),
    @UniqueConstraint(columnNames = {"tipoIdentificacion", "identificacion"})
})
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false, length = 34)
    private String primerNombre;

    @Column(length = 34)
    private String segundoNombre;

    @Column(nullable = false, length = 34)
    private String primerApellido;

    @Column(length = 34)
    private String segundoApellido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIdentificacion tipoIdentificacion;

    @Column(nullable = false, length = 11)
    private Integer identificacion;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(nullable = false, length = 80)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Profesion profesion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    @Column(nullable = false, length = 60)
    private String correo;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRol estadoRol = EstadoRol.Activo;

    @Column(nullable = false, length = 255)
    private String foto;

    // ===================== ENUMS ======================

    public enum TipoIdentificacion {
        Cedula_ciudadania, Pasaporte
    }

    public enum Profesion {
        Ing_sistemas, Ing_software, Ing_basedatos, Ing_redes, Analista_datos, Diseno_grafico
    }

    public enum Especialidad {
        Desarrollo_web, Analisis_software, Diseno_web, Desarrollo_backend, Desarrollo_frontend,
        Diseno_basesdatos, FullStack, Arquitectura_software, Ciberseguridad, Administracion_servidores
    }

    public enum Rol {
        Lider, Coordinador, Desarrollador
    }

    public enum EstadoRol {
        Activo, Inactivo
    }

    @Column(unique = true, length = 100)
    private String token;




    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null && Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            throw new IllegalArgumentException("El usuario debe tener al menos 18 años.");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Profesion getProfesion() {
        return profesion;
    }

    public void setProfesion(Profesion profesion) {
        this.profesion = profesion;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public EstadoRol getEstadoRol() {
        return estadoRol;
    }

    public void setEstadoRol(EstadoRol estadoRol) {
        this.estadoRol = estadoRol;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
