package edu.fateczl.locadoraveiculos.veiculo;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "categoriaNome", source = "categoria.nome")
    @Mapping(target = "valorDiaria", source = "categoria.valorDiaria")
    VeiculoDTO toDTO(Veiculo veiculo);

    @Mapping(target = "categoria.id", source = "categoriaId")
    @Mapping(target = "locacoes", ignore = true)
    @Mapping(target = "reparos", ignore = true)
    Veiculo toEntity(VeiculoDTO dto);

    @Mapping(target = "categoria.id", source = "categoriaId")
    @Mapping(target = "locacoes", ignore = true)
    @Mapping(target = "reparos", ignore = true)
    void updateFromDTO(VeiculoDTO dto, @MappingTarget Veiculo veiculo);
}