package edu.fateczl.locadoraveiculos;

import edu.fateczl.locadoraveiculos.categoria.CategoriaService;
import edu.fateczl.locadoraveiculos.locacao.LocacaoService;
import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class HomeController {

    private final CategoriaService categoriaService;
    private final VeiculoService veiculoService;
    private final LocatarioService locatarioService;
    private final LocacaoService locacaoService;

    HomeController(
            CategoriaService categoriaService,
            VeiculoService veiculoService,
            LocatarioService locatarioService,
            LocacaoService locacaoService
    ) {
        this.categoriaService = categoriaService;
        this.veiculoService = veiculoService;
        this.locatarioService = locatarioService;
        this.locacaoService = locacaoService;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        var veiculos = veiculoService.listarTodos();
        var locacoes = locacaoService.listarTodas();

        model.addAttribute("totalCategorias", categoriaService.listarTodas().size());
        model.addAttribute("totalVeiculos", veiculos.size());
        model.addAttribute("veiculosDisponiveis", veiculos.stream()
                .filter(veiculo -> veiculo.status() != null && "DISPONIVEL".equals(veiculo.status().toString()))
                .count());
        model.addAttribute("totalLocatarios", locatarioService.listarTodos().size());
        model.addAttribute("locacoesAtivas", locacoes.stream()
                .filter(locacao -> locacao.status() != null && "ATIVA".equals(locacao.status().toString()))
                .count());

        return "home";
    }
}
