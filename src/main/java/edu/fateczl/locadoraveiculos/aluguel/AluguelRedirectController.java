package edu.fateczl.locadoraveiculos.aluguel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/alugar")
class AluguelRedirectController {

    @GetMapping
    String redirecionarParaNovaLocacao() {
        return "redirect:/locacoes/nova";
    }

    @GetMapping("/{placa}")
    String redirecionarParaNovaLocacaoComVeiculo(@PathVariable String placa) {
        return "redirect:/locacoes/nova/" + placa;
    }
}
