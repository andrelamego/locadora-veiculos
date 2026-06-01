package edu.fateczl.locadoraveiculos.locacao;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CadastroLocacaoDTO(
        String placa,
        String cpf,
        String nome,
        String numeroHabilitacao,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataNascimento,
        String logradouro,
        String numero,
        String cep,
        String cidade,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataRetirada,
        Integer quantidadeDias
) {}
