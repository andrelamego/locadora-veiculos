package edu.fateczl.locadoraveiculos.devolucao;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DevolucaoMapper {

    @Mapping(target = "locacaoId", source = "locacao.id")
    @Mapping(target = "veiculoPlaca", source = "locacao.veiculo.placa")
    @Mapping(target = "veiculoModelo", source = "locacao.veiculo.modelo")
    @Mapping(target = "locatarioNome", source = "locacao.locatario.nome")
    DevolucaoDTO toDTO(Devolucao devolucao);

    @Mapping(target = "locacao.id", source = "locacaoId")
    Devolucao toEntity(DevolucaoDTO dto);
}