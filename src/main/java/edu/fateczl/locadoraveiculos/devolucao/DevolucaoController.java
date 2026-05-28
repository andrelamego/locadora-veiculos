package edu.fateczl.locadoraveiculos.devolucao;

import edu.fateczl.locadoraveiculos.locacao.LocacaoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/devolucoes")
class DevolucaoController {

    private final DevolucaoService service;
    private final LocacaoService locacaoService;

    DevolucaoController(DevolucaoService service, LocacaoService locacaoService) {
        this.service = service;
        this.locacaoService = locacaoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("devolucoes", service.listarTodas());
        return "devolucao/list";
    }

    @GetMapping("/nova/{locacaoId}")
    public String formulario(@PathVariable Long locacaoId, Model model) {
        model.addAttribute("locacao", locacaoService.buscarPorId(locacaoId));
        model.addAttribute("dataDevolucao", LocalDate.now());
        return "devolucao/form";
    }

    @PostMapping
    public String registrar(@RequestParam Long locacaoId,
                            @RequestParam BigDecimal litrosFaltantes,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataDevolucao,
                            RedirectAttributes ra) {
        try {
            DevolucaoDTO dto = service.registrar(locacaoId, litrosFaltantes, dataDevolucao);
            ra.addFlashAttribute("sucesso", "Devolução registrada! Valor total: R$ " + dto.valorTotal());
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/devolucoes";
    }

    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model) {
        model.addAttribute("devolucao", service.buscarPorId(id));
        return "devolucao/detalhe";
    }
}

