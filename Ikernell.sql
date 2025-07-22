CREATE DATABASE ikernell;
use ikernell;
CREATE TABLE usuario (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    primer_nombre VARCHAR(34) NOT NULL,
    segundo_nombre VARCHAR(34),
    primer_apellido VARCHAR(34) NOT NULL,
    segundo_apellido VARCHAR(34),
    tipo_identificacion enum('Cedula_ciudadania','Pasaporte'),
    identificacion int(11),
    fecha_nacimiento date not null,
    direccion varchar(80) not null,
    profesion enum ('Ing_sistemas','Ing_software','Ing_basedatos','Ing_redes','Analista_datos','Diseno_grafico') not null,
    especialidad enum('Desarrollo_web','Analisis_software','Diseno_web','Desarrollo_backend','Desarrollo_frontend','Diseno_basesdatos','FullStack','Arquitectura_software','Ciberseguridad','Adminiusuariostracion_servidores') not null,
    correo VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    rol ENUM('Lider', 'Coordinador', 'Desarrollador'),
    estado_rol ENUM('Activo', 'Inactivo') NOT NULL,
    foto VARCHAR(255) NOT NULL
);

CREATE TABLE proyecto (
    id_proyecto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(40) NOT NULL,
    descripcion TINYTEXT NOT NULL,
    estado ENUM('Pendiente', 'En_progreso', 'Finalizado', 'Cancelado', 'Interrumpido') DEFAULT 'Pendiente',
    fecha_inicio TIMESTAMP NULL DEFAULT NULL,
    fecha_fin TIMESTAMP NULL DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    fecha_fin_estimada DATE NOT NULL
);

CREATE TABLE etapa (
    id_etapa INT PRIMARY KEY AUTO_INCREMENT,
    fase ENUM('Analisis', 'Diseño', 'Desarrollo', 'Pruebas', 'Documentacion', 'Despliegue', 'Mantenimiento'),
    id_proyecto INT NOT NULL,
    FOREIGN KEY (id_proyecto)
        REFERENCES proyecto (id_proyecto)
);
create table actividad(
id_actividad int primary key auto_increment,
tipo_actividad enum('Analisis_requerimientos',
'Diseño_sistema','Codificacion','Pruebas_unitarias',
'Pruebas_integracion','Ajuste_errores','Documentacion_tecnica','Revision_codigo','Investigacion_tecnica','Despliegue','Reunion_planificacion','Soporte_tecnico','Capacitacion','Mantenimiento'),
fecha_inicio TIMESTAMP NULL DEFAULT NULL,
fecha_fin TIMESTAMP NULL DEFAULT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
fecha_fin_estimada DATE NOT NULL,
estado enum('Pendiente','En_progreso','Finalizada') default 'Pendiente',
descripcion tinytext not null,
id_desarrollador int not null,
id_etapa int not null,
FOREIGN KEY (id_desarrollador)
	REFERENCES usuario (id_usuario),
FOREIGN KEY (id_etapa)
	REFERENCES etapa(id_etapa)
);

CREATE TABLE interrupcion (
    id_interrupcion INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('Humana', 'Tecnica', 'Externa'),
    fecha DATE NOT NULL,
    duracion TIME NOT NULL,
    fase ENUM('Analisis', 'Diseño', 'Desarrollo', 'Pruebas', 'Documentacion', 'Despliegue', 'Mantenimiento'),
    id_desarrollador INT NOT NULL,
    id_proyecto INT NOT NULL,
    FOREIGN KEY (id_desarrollador)
        REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_proyecto)
        REFERENCES proyecto (id_proyecto)
);

CREATE TABLE error (
    id_error INT PRIMARY KEY AUTO_INCREMENT,
    tipo ENUM('Sintaxis', 'Logico', 'Runtime', 'Compilacion', 'Integracion', 'Conexion'),
    descripcion TINYTEXT,
    id_usuario INT NOT NULL,
    id_proyecto INT NOT NULL,
    FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_proyecto)
        REFERENCES proyecto (id_proyecto)
);

CREATE TABLE asignacion (
    id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_proyecto INT NOT NULL,
    fecha_asignacion DATE NOT NULL,
    rol VARCHAR(20) NOT NULL,
    asignado_por INT NOT NULL,
    estado ENUM('ACTIVO', 'FINALIZADO') DEFAULT 'ACTIVO',
    notas TINYTEXT,
    horas_estimadas INT NOT NULL,
    horas_reales INT NOT NULL,
    FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario),
    FOREIGN KEY (id_proyecto)
        REFERENCES proyecto (id_proyecto),
    FOREIGN KEY (asignado_por)
        REFERENCES usuario (id_usuario)
);
select*from usuario;
ALTER TABLE usuario
ADD COLUMN token VARCHAR(100);

SELECT token, COUNT(*) 
FROM usuario 
GROUP BY token 
HAVING COUNT(*) > 1;
DELETE FROM usuario WHERE token = 'token_duplicado' LIMIT 2;

ALTER TABLE usuario
MODIFY COLUMN token_expiracion TIMESTAMP NULL DEFAULT NULL;

ALTER TABLE asignacion
DROP COLUMN rol;
ALTER TABLE etapa
ADD COLUMN fecha_inicio TIMESTAMP NULL DEFAULT NULL,
ADD COLUMN fecha_fin TIMESTAMP NULL DEFAULT NULL,
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;




