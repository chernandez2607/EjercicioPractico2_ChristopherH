/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.repository;


import Practica2.domain.Usuario;
import Practica2.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para autenticación
    Optional<Usuario> findByCorreo(String correo);

    // Consulta 1: Buscar usuarios por rol
    List<Usuario> findByRol(Rol rol);

    // Consulta personalizada: Buscar usuarios por nombre del rol
    @Query("SELECT u FROM Usuario u WHERE u.rol.nombre = :nombreRol")
    List<Usuario> findByRolNombre(@Param("nombreRol") String nombreRol);

    // Consulta 2: Buscar usuarios creados en un rango de fechas
    List<Usuario> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    // Consulta 3: Buscar usuarios por coincidencia parcial en correo o nombre
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.correo) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
           "OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :criterio, '%')) " +
           "OR LOWER(u.apellido) LIKE LOWER(CONCAT('%', :criterio, '%'))")
    List<Usuario> buscarPorCorreoONombre(@Param("criterio") String criterio);

    // Consulta 4: Contar usuarios activos vs inactivos
    Long countByActivo(Boolean activo);

    // Consulta personalizada para obtener estadísticas
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    Long contarUsuariosActivos();

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = false")
    Long contarUsuariosInactivos();

    // Consulta 5: Obtener usuarios ordenados por fecha de creación
    List<Usuario> findAllByOrderByFechaCreacionDesc();

    // Consulta adicional: Usuarios activos por rol
    List<Usuario> findByActivoAndRol(Boolean activo, Rol rol);

    // Consulta adicional: Buscar por nombre completo
    @Query("SELECT u FROM Usuario u WHERE LOWER(CONCAT(u.nombre, ' ', u.apellido)) " +
           "LIKE LOWER(CONCAT('%', :nombreCompleto, '%'))")
    List<Usuario> buscarPorNombreCompleto(@Param("nombreCompleto") String nombreCompleto);
}