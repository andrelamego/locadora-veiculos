package edu.fateczl.locadoraveiculos.relatorio;

import net.sf.jasperreports.engine.JasperCompileManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RelatorioJrxmlTest {

    @Test
    void deveCompilarLayoutsJasper() throws Exception {
        assertNotNull(JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/veiculos-alugados-dia.jrxml")));
        assertNotNull(JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/historico-cliente.jrxml")));
        assertNotNull(JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/reparos-dia.jrxml")));
    }
}
