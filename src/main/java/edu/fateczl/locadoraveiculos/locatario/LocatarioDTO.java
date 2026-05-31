package edu.fateczl.locadoraveiculos.locatario;

import io.github.andrelamego.brValidator.cpf.ValidCpf;

import java.time.LocalDate;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas

public record LocatarioDTO(
        @ValidCpf String cpf,
        String nome,
        String numeroHabilitacao,
        LocalDate dataNascimento,
        Long enderecoId,
        String enderecoResumo
) {
}