package edu.fateczl.locadoraveiculos.categoria;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/categorias")
class CategoriaController {

    private final CategoriaService service;

    CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", service.listarTodas());
        return "categoria/list";
    }

    @GetMapping("/nova")
    public String formularioNovo(Model model) {
        model.addAttribute("categoria", new CategoriaDTO(null, "", "", null));
        return "categoria/form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", service.buscarPorId(id));
        return "categoria/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute CategoriaDTO dto, RedirectAttributes ra) {
        try {
            service.salvar(dto);
            ra.addFlashAttribute("sucesso", "Categoria salva com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/categorias";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Categoria excluída com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/categorias";
    }
}

