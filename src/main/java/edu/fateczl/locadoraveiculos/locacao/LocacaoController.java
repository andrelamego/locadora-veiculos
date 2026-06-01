package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.categoria.CategoriaService;
import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/locacoes")
class LocacaoController {

    private final LocacaoService service;
    private final VeiculoService veiculoService;
    private final CategoriaService categoriaService;

    LocacaoController(LocacaoService service, VeiculoService veiculoService, CategoriaService categoriaService) {
        this.service = service;
        this.veiculoService = veiculoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("locacoes", service.listarTodas());
        return "locacao/list";
    }

    @GetMapping("/nova")
    String inicio(@RequestParam(required = false) Long categoriaId, Model model) {
        carregarTela(model, null, categoriaId);
        return "locacao/nova";
    }

    @GetMapping("/nova/{placa}")
    String selecionarVeiculo(
            @PathVariable String placa,
            @RequestParam(required = false) Long categoriaId,
            Model model
    ) {
        carregarTela(model, placa, categoriaId);
        return "locacao/nova";
    }

    @GetMapping("/nova/{placa}/cpf")
    String consultarCpf(
            @PathVariable String placa,
            @RequestParam String cpf,
            @RequestParam(required = false) Long categoriaId,
            Model model
    ) {
        carregarTela(model, placa, categoriaId);
        model.addAttribute("cpf", cpf);

        try {
            Optional<LocatarioDTO> locatario = service.buscarLocatarioPorCpf(cpf);
            model.addAttribute("cpfConsultado", true);
            locatario.ifPresent(dto -> model.addAttribute("locatarioEncontrado", dto));
        } catch (IllegalArgumentException ex) {
            model.addAttribute("erro", ex.getMessage());
        }

        return "locacao/nova";
    }

    @PostMapping
    public String registrar(@ModelAttribute LocacaoDTO dto, RedirectAttributes ra) {
        try {
            service.registrar(dto);
            ra.addFlashAttribute("sucesso", "Locacao registrada com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/locacoes";
    }

    @PostMapping("/nova/confirmar")
    String confirmarLocacao(
            @RequestParam String placa,
            @RequestParam String cpf,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetirada,
            @RequestParam Integer quantidadeDias,
            RedirectAttributes redirectAttributes
    ) {
        try {
            service.confirmarLocacao(placa, cpf, dataRetirada, quantidadeDias);
            redirectAttributes.addFlashAttribute("sucesso", "Locacao confirmada com sucesso.");
            return "redirect:/locacoes/nova";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/locacoes/nova/" + placa;
        }
    }

    @PostMapping("/nova/cadastrar-confirmar")
    String cadastrarLocatarioEConfirmar(
            @ModelAttribute CadastroLocacaoDTO dto,
            RedirectAttributes redirectAttributes
    ) {
        try {
            service.cadastrarLocatarioEConfirmar(dto);
            redirectAttributes.addFlashAttribute("sucesso", "Cadastro e locacao confirmados com sucesso.");
            return "redirect:/locacoes/nova";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/locacoes/nova/" + dto.placa();
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Locacao excluida com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Nao foi possivel excluir: " + e.getMessage());
        }
        return "redirect:/locacoes";
    }

    private void carregarTela(Model model, String placaSelecionada, Long categoriaId) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("categoriaSelecionadaId", categoriaId);
        model.addAttribute("veiculos", categoriaId == null
                ? veiculoService.listarDisponiveis()
                : veiculoService.listarDisponiveisPorCategoria(categoriaId));
        model.addAttribute("dataRetirada", LocalDate.now());
        model.addAttribute("quantidadeDias", 1);

        if (placaSelecionada != null) {
            model.addAttribute("veiculoSelecionado", veiculoService.buscarPorPlaca(placaSelecionada));
        }
    }
}
