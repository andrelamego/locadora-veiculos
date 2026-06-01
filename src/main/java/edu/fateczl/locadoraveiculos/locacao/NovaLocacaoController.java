package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/locacoes/nova")
class NovaLocacaoController {

    private final VeiculoService veiculoService;
    private final LocacaoUsuarioService locacaoUsuarioService;

    NovaLocacaoController(VeiculoService veiculoService, LocacaoUsuarioService locacaoUsuarioService) {
        this.veiculoService = veiculoService;
        this.locacaoUsuarioService = locacaoUsuarioService;
    }

    @GetMapping
    String inicio(Model model) {
        carregarTela(model, null);
        return "locacao/nova";
    }

    @GetMapping("/{placa}")
    String selecionarVeiculo(@PathVariable String placa, Model model) {
        carregarTela(model, placa);
        return "locacao/nova";
    }

    @GetMapping("/{placa}/cpf")
    String consultarCpf(@PathVariable String placa, @RequestParam String cpf, Model model) {
        carregarTela(model, placa);
        model.addAttribute("cpf", cpf);

        try {
            Optional<LocatarioDTO> locatario = locacaoUsuarioService.buscarLocatarioPorCpf(cpf);
            model.addAttribute("cpfConsultado", true);
            locatario.ifPresent(dto -> model.addAttribute("locatarioEncontrado", dto));
        } catch (IllegalArgumentException ex) {
            model.addAttribute("erro", ex.getMessage());
        }

        return "locacao/nova";
    }

    @PostMapping("/confirmar")
    String confirmarLocacao(
            @RequestParam String placa,
            @RequestParam String cpf,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetirada,
            @RequestParam Integer quantidadeDias,
            RedirectAttributes redirectAttributes
    ) {
        try {
            locacaoUsuarioService.confirmarLocacao(placa, cpf, dataRetirada, quantidadeDias);
            redirectAttributes.addFlashAttribute("sucesso", "Locacao confirmada com sucesso.");
            return "redirect:/locacoes/nova";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/locacoes/nova/" + placa;
        }
    }

    @PostMapping("/cadastrar-confirmar")
    String cadastrarLocatarioEConfirmar(
            @RequestParam String placa,
            @RequestParam String cpf,
            @RequestParam String nome,
            @RequestParam String numeroHabilitacao,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataNascimento,
            @RequestParam String logradouro,
            @RequestParam String numero,
            @RequestParam String cep,
            @RequestParam String cidade,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataRetirada,
            @RequestParam Integer quantidadeDias,
            RedirectAttributes redirectAttributes
    ) {
        try {
            locacaoUsuarioService.cadastrarLocatarioEConfirmar(
                    placa,
                    cpf,
                    nome,
                    numeroHabilitacao,
                    dataNascimento,
                    logradouro,
                    numero,
                    cep,
                    cidade,
                    dataRetirada,
                    quantidadeDias
            );
            redirectAttributes.addFlashAttribute("sucesso", "Cadastro e locacao confirmados com sucesso.");
            return "redirect:/locacoes/nova";
        } catch (IllegalArgumentException | IllegalStateException | EntityNotFoundException ex) {
            redirectAttributes.addFlashAttribute("erro", ex.getMessage());
            return "redirect:/locacoes/nova/" + placa;
        }
    }

    private void carregarTela(Model model, String placaSelecionada) {
        model.addAttribute("veiculos", veiculoService.listarDisponiveis());
        model.addAttribute("dataRetirada", LocalDate.now());
        model.addAttribute("quantidadeDias", 1);

        if (placaSelecionada != null) {
            model.addAttribute("veiculoSelecionado", veiculoService.buscarPorPlaca(placaSelecionada));
        }
    }
}
