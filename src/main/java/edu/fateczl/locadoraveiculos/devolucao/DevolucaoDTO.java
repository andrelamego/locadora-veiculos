package edu.fateczl.locadoraveiculos.devolucao;

import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas

    public record DevolucaoDTO(
            Long id,
            Long locacaoId,
            String veiculoPlaca,
            String veiculoModelo,
            String locatarioNome,
            LocalDate dataDevolucao,
            BigDecimal litrosFaltantes,
            BigDecimal valorCombustivel,
            BigDecimal valorLocacao,
            BigDecimal valorTotal
    ) {}
