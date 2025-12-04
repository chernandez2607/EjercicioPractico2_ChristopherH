/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.service;



import Practica2.domain.Usuario;
import Practica2.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        // Si es un nuevo usuario, encriptar la contraseña
        if (usuario.getIdUsuario() == null) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        } else {
            // Si es actualización y la contraseña no ha cambiado, mantener la anterior
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
            if (usuarioExistente != null && 
                !usuario.getContrasena().startsWith("$2a$")) {
                usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            } else if (usuarioExistente != null) {
                usuario.setContrasena(usuarioExistente.getContrasena());
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorRolNombre(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return usuarioRepository.findByFechaCreacionBetween(inicio, fin);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorCriterio(String criterio) {
        return usuarioRepository.buscarPorCorreoONombre(criterio);
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarUsuariosActivos() {
        return usuarioRepository.contarUsuariosActivos();
    }

    @Override
    @Transactional(readOnly = true)
    public Long contarUsuariosInactivos() {
        return usuarioRepository.contarUsuariosInactivos();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarOrdenadosPorFecha() {
        return usuarioRepository.findAllByOrderByFechaCreacionDesc();
    }
}