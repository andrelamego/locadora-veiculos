package edu.fateczl.locadoraveiculos.endereco;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    EnderecoDTO toDTO(Endereco endereco);
    Endereco toEntity(EnderecoDTO dto);
    void updateFromDTO(EnderecoDTO dto, @MappingTarget Endereco endereco);
}