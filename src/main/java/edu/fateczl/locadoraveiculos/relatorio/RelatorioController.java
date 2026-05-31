package edu.fateczl.locadoraveiculos.relatorio;

import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/relatorios")
class RelatorioController {

    private final RelatorioService relatorioService;
    private final LocatarioService locatarioService;

    RelatorioController(RelatorioService relatorioService, LocatarioService locatarioService) {
        this.relatorioService = relatorioService;
        this.locatarioService = locatarioService;
    }

    @GetMapping
    public String menu(Model model) {
        model.addAttribute("hoje", LocalDate.now());
        model.addAttribute("locatarios", locatarioService.listarTodos());
        return "relatorios/menu";
    }

    @GetMapping("/veiculos-alugados-dia")
    public ResponseEntity<byte[]> veiculosAlugadosNoDia(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data
    ) {
        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        byte[] pdf = relatorioService.gerarVeiculosAlugadosNoDia(dataConsulta);
        return pdf("veiculos-alugados-dia.pdf", pdf);
    }

    @GetMapping("/historico-cliente")
    public ResponseEntity<byte[]> historicoClientePorParametro(@RequestParam String cpf) {
        return historicoCliente(cpf);
    }

    @GetMapping("/historico-cliente/{cpf}")
    public ResponseEntity<byte[]> historicoCliente(@PathVariable String cpf) {
        byte[] pdf = relatorioService.gerarHistoricoCliente(cpf);
        return pdf("historico-cliente.pdf", pdf);
    }

    @GetMapping("/reparos-dia")
    public ResponseEntity<byte[]> reparosNoDia(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate data
    ) {
        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        byte[] pdf = relatorioService.gerarReparosNoDia(dataConsulta);
        return pdf("reparos-dia.pdf", pdf);
    }

    private ResponseEntity<byte[]> pdf(String nomeArquivo, byte[] conteudo) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nomeArquivo)
                .contentType(MediaType.APPLICATION_PDF)
                .body(conteudo);
    }
}
