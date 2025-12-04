/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Practica2.controller;



import Practica2.domain.Usuario;
import Practica2.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class InicioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping({"/", "/home", "/index"})
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correo = auth.getName();
        Usuario usuario = usuarioService.buscarPorCorreo(correo);
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("nombreCompleto", usuario.getNombreCompleto());
        model.addAttribute("rol", usuario.getRol().getNombre());
        
        return "index";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "acceso-denegado";
    }
}