package edu.fateczl.locadoraveiculos;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequisitosFuncionaisTest {

    private static final Path ROOT = Path.of("").toAbsolutePath();

    @Test
    void devePossuirArtefatosParaTodosOsRequisitosFuncionais() throws Exception {
        String schema = ler("src/main/resources/db/01_tabelas_sqlserver.sql");
        String functions = ler("src/main/resources/db/03_functions_sqlserver.sql");
        String procedures = ler("src/main/resources/db/04_procedures_sqlserver.sql");
        String relatorioController = ler("src/main/java/edu/fateczl/locadoraveiculos/relatorio/RelatorioController.java");
        String veiculoController = ler("src/main/java/edu/fateczl/locadoraveiculos/veiculo/VeiculoController.java");
        String veiculoRepository = ler("src/main/java/edu/fateczl/locadoraveiculos/veiculo/VeiculoRepository.java");
        String devolucaoService = ler("src/main/java/edu/fateczl/locadoraveiculos/devolucao/DevolucaoService.java");
        String relatoriosMenu = ler("src/main/webapp/WEB-INF/views/relatorios/menu.jsp");

        assertAll(
                () -> assertTrue(schema.contains("CREATE TABLE dbo.categoria"), "RF01: tabela de categoria"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.veiculo"), "RF02: tabela de veiculo"),
                () -> assertTrue(functions.contains("fn_carros_disponiveis"), "RF03: UDF de veiculos disponiveis"),
                () -> assertTrue(procedures.contains("CURSOR"), "RF03: cursor para consulta de disponiveis"),
                () -> assertTrue(veiculoController.contains("/disponiveis"), "RF03: consulta disponivel na controller"),
                () -> assertTrue(veiculoRepository.contains("listarDisponiveisPorCategoria"), "RF03: repository consulta disponiveis"),
                () -> assertTrue(Files.exists(ROOT.resolve("src/main/webapp/WEB-INF/views/veiculo/disponiveis.jsp")), "RF03: view de disponiveis"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.locatario"), "RF04: tabela de locatario"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.endereco"), "RF05: tabela de endereco"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.locacao"), "RF06: tabela de locacao"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.devolucao"), "RF07: tabela de devolucao"),
                () -> assertTrue(devolucaoService.contains("VALOR_LITRO_GASOLINA") && devolucaoService.contains("VALOR_LITRO_ALCOOL"), "RF08: calculo por combustivel"),
                () -> assertTrue(schema.contains("CREATE TABLE dbo.reparo"), "RF09: tabela de reparo"),
                () -> assertTrue(functions.contains("fn_veiculos_alugados_no_dia"), "RF10/RF11: UDF de alugados no dia"),
                () -> assertTrue(functions.contains("fn_historico_cliente"), "RF12: UDF de historico do cliente"),
                () -> assertTrue(functions.contains("fn_reparos_no_dia"), "RF13: UDF de reparos no dia"),
                () -> assertTrue(relatorioController.contains("/veiculos-alugados-dia"), "RF11: rota relatorio alugados"),
                () -> assertTrue(relatorioController.contains("/historico-cliente"), "RF12: rota relatorio historico"),
                () -> assertTrue(relatorioController.contains("/reparos-dia"), "RF13: rota relatorio reparos"),
                () -> assertTrue(relatoriosMenu.contains("veiculos-alugados-dia") && relatoriosMenu.contains("historico-cliente") && relatoriosMenu.contains("reparos-dia"), "RF14: relatorios disponiveis na view"),
                () -> assertTrue(Files.exists(ROOT.resolve("src/main/resources/reports/veiculos-alugados-dia.jrxml")), "RF11: JRXML alugados"),
                () -> assertTrue(Files.exists(ROOT.resolve("src/main/resources/reports/historico-cliente.jrxml")), "RF12: JRXML historico"),
                () -> assertTrue(Files.exists(ROOT.resolve("src/main/resources/reports/reparos-dia.jrxml")), "RF13: JRXML reparos")
        );
    }

    private String ler(String caminho) throws Exception {
        return Files.readString(ROOT.resolve(caminho));
    }
}
