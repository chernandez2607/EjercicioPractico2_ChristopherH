/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.service;


import Practica2.domain.Usuario;
import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioService {

    List<Usuario> listarUsuarios();

    Usuario buscarUsuarioPorId(Long id);

    Usuario guardarUsuario(Usuario usuario);

    void eliminarUsuario(Long id);

    Usuario buscarPorCorreo(String correo);

    // Consultas avanzadas
    List<Usuario> buscarPorRolNombre(String nombreRol);

    List<Usuario> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    List<Usuario> buscarPorCriterio(String criterio);

    Long contarUsuariosActivos();

    Long contarUsuariosInactivos();

    List<Usuario> listarOrdenadosPorFecha();
}