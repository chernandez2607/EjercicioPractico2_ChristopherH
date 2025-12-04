/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.controller;

import Practica2.domain.Rol;
import Practica2.service.RolService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
@Slf4j
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listar(Model model) {
        var roles = rolService.listarRoles();
        model.addAttribute("roles", roles);
        return "roles/listado";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("rol") Rol rol, 
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "roles/formulario";
        }

        try {
            rolService.guardarRol(rol);
            return "redirect:/roles?exito";
        } catch (Exception e) {
            log.error("Error al guardar rol", e);
            model.addAttribute("error", "Error al guardar el rol: " + e.getMessage());
            return "roles/formulario";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Rol rol = rolService.buscarRolPorId(id);
        if (rol == null) {
            return "redirect:/roles?error";
        }
        model.addAttribute("rol", rol);
        return "roles/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        try {
            rolService.eliminarRol(id);
            return "redirect:/roles?eliminado";
        } catch (Exception e) {
            log.error("Error al eliminar rol", e);
            return "redirect:/roles?errorEliminar";
        }
    }
}