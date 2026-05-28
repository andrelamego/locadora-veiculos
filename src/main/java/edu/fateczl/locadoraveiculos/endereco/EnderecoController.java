package edu.fateczl.locadoraveiculos.endereco;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


// SOLID - SRP: cada Controller trata apenas requisições HTTP da sua entidade
// SOLID - DIP: Controllers dependem de Services (abstração), não de implementações

@Controller
@RequestMapping("/enderecos")
class EnderecoController {

    private final EnderecoService service;

    EnderecoController(EnderecoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("enderecos", service.listarTodos());
        return "endereco/list";
    }

    @GetMapping("/novo")
    public String formularioNovo(Model model) {
        model.addAttribute("endereco", new EnderecoDTO(null, "", "", "", ""));
        return "endereco/form";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("endereco", service.buscarPorId(id));
        return "endereco/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute EnderecoDTO dto, RedirectAttributes ra) {
        try {
            service.salvar(dto);
            ra.addFlashAttribute("sucesso", "Endereço salvo com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/enderecos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("sucesso", "Endereço excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("erro", "Não foi possível excluir: " + e.getMessage());
        }
        return "redirect:/enderecos";
    }
}


