package edu.fateczl.locadoraveiculos.locatario;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface LocatarioMapper {

    @Mapping(target = "enderecoId", source = "endereco.id")
    @Mapping(target = "enderecoResumo", expression = "java(formatarEndereco(locatario))")
    LocatarioDTO toDTO(Locatario locatario);

    @Mapping(target = "endereco.id", source = "enderecoId")
    @Mapping(target = "locacoes", ignore = true)
    Locatario toEntity(LocatarioDTO dto);

    @Mapping(target = "endereco.id", source = "enderecoId")
    @Mapping(target = "locacoes", ignore = true)
    void updateFromDTO(LocatarioDTO dto, @MappingTarget Locatario locatario);

    default String formatarEndereco(Locatario locatario) {
        if (locatario == null || locatario.getEndereco() == null) {
            return null;
        }

        return locatario.getEndereco().getLogradouro()
                + ", "
                + locatario.getEndereco().getNumero()
                + " - "
                + locatario.getEndereco().getCidade();
    }
}
