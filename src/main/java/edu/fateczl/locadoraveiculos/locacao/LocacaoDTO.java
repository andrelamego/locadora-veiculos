package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.enums.StatusLocacao;

import java.time.LocalDate;

public record LocacaoDTO(
        Long id,
        String veiculoPlaca,
        String veiculoModelo,
        String locatarioCpf,
        String locatarioNome,
        LocalDate dataRetirada,
        Integer quantidadeDias,
        LocalDate dataPrevistaDevolucao,
        StatusLocacao status
) {}
