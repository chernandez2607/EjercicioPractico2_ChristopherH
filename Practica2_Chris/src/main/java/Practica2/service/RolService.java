/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.service;



import Practica2.domain.Rol;
import java.util.List;

public interface RolService {

    List<Rol> listarRoles();

    Rol buscarRolPorId(Long id);

    Rol guardarRol(Rol rol);

    void eliminarRol(Long id);

    Rol buscarPorNombre(String nombre);
}
