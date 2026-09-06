package br.com.fiap.clyvo_companion.controller.web;

import br.com.fiap.clyvo_companion.service.AgendamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vet/consultas")
public class VeterinarioConsultaController {

    private final AgendamentoService agendamentoService;

    public VeterinarioConsultaController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarTodos());
        return "vet/consultas";
    }
}
