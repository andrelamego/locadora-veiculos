package edu.fateczl.locadoraveiculos.categoria;

import org.mapstruct.*;

// SOLID - SRP: cada mapper é responsável apenas pela conversão de uma entidade

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDTO toDTO(Categoria categoria);

    @Mapping(target = "veiculos", ignore = true)
    Categoria toEntity(CategoriaDTO dto);

    @Mapping(target = "veiculos", ignore = true)
    void updateFromDTO(CategoriaDTO dto, @MappingTarget Categoria categoria);
}
