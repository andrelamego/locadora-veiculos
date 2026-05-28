package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/locacoes")
class LocacaoController {

    private final LocacaoService service;
    private final VeiculoService veiculoService;
    private final LocatarioService locatarioService;

    LocacaoController(LocacaoService service, VeiculoService veiculoService, LocatarioService locatarioService) {
        this.service = service;
        this.veiculoService = veiculoService;
        this.locatarioService = locatarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locacoes", service.listarTodas());
        return "locacao/list";
    }

    @GetMapping("/nova")
    public String formularioNovo(@RequestParam(required = false) String placa, Model model) {
        model.addAttribute("locacao", new LocacaoDTO(null, placa, null, null, null, LocalDate.now(), 1, null, null));
        model.addAttribute("veiculos", veiculoService.listarTodos());
        model.addAttribute("locatarios", locatarioService.listarTodos());
        return "locacao/form";
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

