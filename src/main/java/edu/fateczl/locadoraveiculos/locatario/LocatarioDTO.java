package edu.fateczl.locadoraveiculos.locatario;

import java.time.LocalDate;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas

    public record LocatarioDTO(
            String cpf,
            String nome,
            String numeroHabilitacao,
            LocalDate dataNascimento,
            Long enderecoId,
            String enderecoResumo
    ) {}
