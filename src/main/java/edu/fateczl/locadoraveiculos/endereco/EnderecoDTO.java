package edu.fateczl.locadoraveiculos.endereco;

// SOLID - SRP: DTOs responsáveis apenas por transporte de dados entre camadas
    public record EnderecoDTO(
            Long id,
            String logradouro,
            String numero,
            String cep,
            String cidade
    ) {}
