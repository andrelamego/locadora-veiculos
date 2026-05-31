package edu.fateczl.locadoraveiculos.relatorio;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// SOLID - SRP: responsável apenas pela geração de relatórios PDF
// SOLID - OCP: novos relatórios podem ser adicionados sem alterar os existentes
@Service
public class RelatorioService {

    private final DataSource dataSource;

    // SOLID - DIP: depende da abstração DataSource, não de implementação concreta
    public RelatorioService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // RF-15: relatório de veículos alugados no dia
    public byte[] gerarVeiculosAlugadosNoDia(LocalDate data) {
        Map<String, Object> params = new HashMap<>();
        params.put("DATA_CONSULTA", Date.valueOf(data));
        return gerarPdf("/reports/veiculos-alugados-dia.jrxml", params);
    }

    // RF-16: relatório de histórico do cliente
    public byte[] gerarHistoricoCliente(String cpf) {
        Map<String, Object> params = new HashMap<>();
        params.put("CPF_CLIENTE", cpf);
        return gerarPdf("/reports/historico-cliente.jrxml", params);
    }

    // RF-17: relatório de reparos no dia
    public byte[] gerarReparosNoDia(LocalDate data) {
        Map<String, Object> params = new HashMap<>();
        params.put("DATA_CONSULTA", Date.valueOf(data));
        return gerarPdf("/reports/reparos-dia.jrxml", params);
    }

    private byte[] gerarPdf(String caminhoJrxml, Map<String, Object> params) {
        try (Connection connection = dataSource.getConnection()) {
            InputStream arquivo = getClass().getResourceAsStream(caminhoJrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(arquivo);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, connection);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF: " + e.getMessage(), e);
        }
    }
}