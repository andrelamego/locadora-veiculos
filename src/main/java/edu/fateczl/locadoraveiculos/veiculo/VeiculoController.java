package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.categoria.CategoriaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/veiculos")
class VeiculoController {

    private final VeiculoService service;
    private final CategoriaService categoriaService;

    VeiculoController(VeiculoService service, CategoriaService categoriaService) {
        this.service = service;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("veiculos", service.listarTodos());
        return "veiculo/list";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("veiculo", new VeiculoDTO(null, "", "", "", null, null, null, null, null, null, null, null, null));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("tiposCombustivel", edu.fateczl.locadoraveiculos.enums.TipoCombustivel.values());
        model.addAttribute("tiposCambio", edu.fateczl.locadoraveiculos.enums.TipoCambio.values());
        model.addAttribute("statusVeiculo", edu.fateczl.locadoraveiculos.enums.StatusVeiculo.values());
        return "veiculo/form";
    }

    @GetMapping("/editar/{placa}")
    public String formularioEditar(@PathVariable String placa, Model model) {
        model.addAttribute("veiculo", service.buscarPorPlaca(placa));
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("tiposCombustivel", edu.fateczl.locadoraveiculos.enums.TipoCombustivel.values());
        model.addAttribute("tiposCambio", edu.fateczl.locadoraveiculos.enums.TipoCambio.values());
        model.addAttribute("statusVeiculo", edu.fateczl.locadoraveiculos.enums.StatusVeiculo.values());
        return "veiculo/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute VeiculoDTO dto, RedirectAttributes ra) {
        try {
            service.salvar(dto);
            ra.addFlashAttribute("sucesso", "Veículo salvo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/veiculos";
    }

    @GetMapping("/excluir/{placa}")
    public String excluir(@PathVariable String placa, RedirectAttributes ra) {
        try {
            service.excluir(placa);
            ra.addFlashAttribute("sucesso", "Veículo excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/veiculos";
    }

    // RF-03: consulta veículos disponíveis por categoria
    @GetMapping("/disponiveis")
    public String disponiveis(@RequestParam(required = false) Long categoriaId, Model model) {
        model.addAttribute("categorias", categoriaService.listarTodas());
        if (categoriaId != null) {
            model.addAttribute("veiculos", service.listarDisponiveisPorCategoria(categoriaId));
            model.addAttribute("categoriaSelecionada", categoriaId);
        }
        return "veiculo/disponiveis";
    }
}
