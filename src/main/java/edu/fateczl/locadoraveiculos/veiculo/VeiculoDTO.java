package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.enums.TipoCambio;
import edu.fateczl.locadoraveiculos.enums.TipoCombustivel;
import java.math.BigDecimal;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas

    public record VeiculoDTO(
            String placa,
            String marca,
            String modelo,
            String cor,
            Integer ano,
            TipoCombustivel tipoCombustivel,
            Integer quilometragem,
            TipoCambio tipoCambio,
            BigDecimal capacidadeTanque,
            StatusVeiculo status,
            Long categoriaId,
            String categoriaNome,
            BigDecimal valorDiaria
    ) {}
