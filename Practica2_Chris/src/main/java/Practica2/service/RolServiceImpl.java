/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.service;



import Practica2.domain.Rol;
import Practica2.repository.RolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class RolServiceImpl{

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Rol buscarRolPorId(Long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    @Transactional
    public void eliminarRol(Long id) {
        rolRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Rol buscarPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre).orElse(null);
    }
}