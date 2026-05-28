package edu.fateczl.locadoraveiculos.categoria;

import java.math.BigDecimal;


// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas


    public record CategoriaDTO(
            Long id,
            String nome,
            String descricao,
            BigDecimal valorDiaria
    ) {}
