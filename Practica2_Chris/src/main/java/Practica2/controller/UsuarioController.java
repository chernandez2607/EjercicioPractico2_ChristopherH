/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.controller;

import Practica2.domain.Usuario;
import Practica2.service.UsuarioService;
import Practica2.service.RolService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
@Slf4j
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model) {
        var usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listado";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.listarRoles());
        return "usuarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") Usuario usuario, 
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/formulario";
        }

        try {
            usuarioService.guardarUsuario(usuario);
            return "redirect:/usuarios?exito";
        } catch (Exception e) {
            log.error("Error al guardar usuario", e);
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("roles", rolService.listarRoles());
            return "usuarios/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios?error";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarRoles());
        return "usuarios/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return "redirect:/usuarios?eliminado";
        } catch (Exception e) {
            log.error("Error al eliminar usuario", e);
            return "redirect:/usuarios?errorEliminar";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario == null) {
            return "redirect:/usuarios?error";
        }
        model.addAttribute("usuario", usuario);
        return "usuarios/detalle";
    }
}