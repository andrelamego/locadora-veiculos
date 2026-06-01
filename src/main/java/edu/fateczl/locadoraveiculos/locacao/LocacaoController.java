package edu.fateczl.locadoraveiculos.locacao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/locacoes")
class LocacaoController {

    private final LocacaoService service;

    LocacaoController(LocacaoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locacoes", service.listarTodas());
        return "locacao/list";
    }

    @PostMapping
    public String registrar(@ModelAttribute LocacaoDTO dto, RedirectAttributes ra) {
        try {
            service.registrar(dto);
            ra.addFlashAttribute("sucesso", "Locação registrada com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/locacoes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Locação excluída com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/locacoes";
    }
}

