package edu.fateczl.locadoraveiculos.locatario;

import edu.fateczl.locadoraveiculos.endereco.EnderecoService;
import edu.fateczl.locadoraveiculos.locacao.LocacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações


@Controller
@RequestMapping("/locatarios")
class LocatarioController {

    private final LocatarioService service;
    private final EnderecoService enderecoService;
    private final LocacaoService locacaoService;

    LocatarioController(LocatarioService service, EnderecoService enderecoService, LocacaoService locacaoService) {
        this.service = service;
        this.enderecoService = enderecoService;
        this.locacaoService = locacaoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locatarios", service.listarTodos());
        return "locatario/list";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("locatario", new LocatarioDTO(null, "", "", null, null, null));
        model.addAttribute("enderecos", enderecoService.listarTodos());
        return "locatario/form";
    }

    @GetMapping("/editar/{cpf}")
    public String formularioEditar(@PathVariable String cpf, Model model) {
        model.addAttribute("locatario", service.buscarPorCpf(cpf));
        model.addAttribute("enderecos", enderecoService.listarTodos());
        return "locatario/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute LocatarioDTO dto, RedirectAttributes ra) {
        try {
            service.salvar(dto);
            ra.addFlashAttribute("sucesso", "Locatário salvo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/locatarios";
    }

    @GetMapping("/excluir/{cpf}")
    public String excluir(@PathVariable String cpf, RedirectAttributes ra) {
        try {
            service.excluir(cpf);
            ra.addFlashAttribute("sucesso", "Locatário excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/locatarios";
    }

    // RF-16: histórico de locações do cliente
    @GetMapping("/{cpf}/historico")
    public String historico(@PathVariable String cpf, Model model) {
        model.addAttribute("locatario", service.buscarPorCpf(cpf));
        model.addAttribute("historico", locacaoService.buscarHistoricoCliente(cpf));
        return "locatario/historico";
    }
}

