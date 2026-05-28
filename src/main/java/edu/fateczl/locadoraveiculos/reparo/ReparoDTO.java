package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.enums.StatusReparo;
import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas
    public record ReparoDTO(
            Long id,
            String veiculoPlaca,
            String veiculoModelo,
            LocalDate dataEntrada,
            Integer quantidadeDias,
            LocalDate dataPrevistaSaida,
            String descricaoProblema,
            BigDecimal valorReparo,
            StatusReparo status
    ) {}

