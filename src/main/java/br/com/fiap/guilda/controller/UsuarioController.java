package br.com.fiap.guilda.controller;

import br.com.fiap.guilda.model.Usuario;
import br.com.fiap.guilda.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", service.listarTodos());
        return "perfil";
    }

    @PostMapping
    public String salvar(@ModelAttribute Usuario usuario) {
        service.salvar(usuario);
        return "redirect:/usuarios";
    }
}
