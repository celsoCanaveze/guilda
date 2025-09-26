package br.com.fiap.guilda.controller;

import br.com.fiap.guilda.model.Missao;
import br.com.fiap.guilda.service.MissaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/missoes")
public class MissaoController {

    private final MissaoService service;

    public MissaoController(MissaoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("missoes", service.listarTodas());
        return "missoes";
    }

    @GetMapping("/nova")
    public String novaMissao(Model model) {
        model.addAttribute("missao", new Missao());
        return "criar-missao";
    }

    @PostMapping
    public String salvar(@ModelAttribute Missao missao) {
        service.salvar(missao);
        return "redirect:/missoes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("missao", service.buscarPorId(id));
        return "editar-missao";
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Missao missao) {
        missao.setId(id);
        service.salvar(missao);
        return "redirect:/missoes";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/missoes";
    }
}
