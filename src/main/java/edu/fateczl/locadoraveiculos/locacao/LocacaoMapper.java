package edu.fateczl.locadoraveiculos.locacao;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LocacaoMapper {

    @Mapping(target = "veiculoPlaca", source = "veiculo.placa")
    @Mapping(target = "veiculoModelo", source = "veiculo.modelo")
    @Mapping(target = "locatarioCpf", source = "locatario.cpf")
    @Mapping(target = "locatarioNome", source = "locatario.nome")
    @Mapping(target = "dataPrevistaDevolucao", expression = "java(locacao.getDataPrevistaDevolucao())")
    LocacaoDTO toDTO(Locacao locacao);

    @Mapping(target = "veiculo.placa", source = "veiculoPlaca")
    @Mapping(target = "locatario.cpf", source = "locatarioCpf")
    @Mapping(target = "devolucao", ignore = true)
    Locacao toEntity(LocacaoDTO dto);
}