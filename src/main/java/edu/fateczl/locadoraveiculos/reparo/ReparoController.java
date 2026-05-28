package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.veiculo.VeiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/reparos")
class ReparoController {

    private final ReparoService service;
    private final VeiculoService veiculoService;

    ReparoController(ReparoService service, VeiculoService veiculoService) {
        this.service = service;
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("reparos", service.listarTodos());
        return "reparo/list";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("reparo", new ReparoDTO(null, null, null, LocalDate.now(), 1, null, "", null, null));
        model.addAttribute("veiculos", veiculoService.listarTodos());
        return "reparo/form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("reparo", service.buscarPorId(id));
        model.addAttribute("veiculos", veiculoService.listarTodos());
        return "reparo/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute ReparoDTO dto, RedirectAttributes ra) {
        try {
            service.salvar(dto);
            ra.addFlashAttribute("sucesso", "Reparo salvo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/reparos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Reparo excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/reparos";
    }
}