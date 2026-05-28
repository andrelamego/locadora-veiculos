package edu.fateczl.locadoraveiculos.reparo;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReparoMapper {

    @Mapping(target = "veiculoPlaca", source = "veiculo.placa")
    @Mapping(target = "veiculoModelo", source = "veiculo.modelo")
    @Mapping(target = "dataPrevistaSaida", expression = "java(reparo.getDataPrevistaSaida())")
    ReparoDTO toDTO(Reparo reparo);

    @Mapping(target = "veiculo.placa", source = "veiculoPlaca")
    Reparo toEntity(ReparoDTO dto);

    @Mapping(target = "veiculo.placa", source = "veiculoPlaca")
    void updateFromDTO(ReparoDTO dto, @MappingTarget Reparo reparo);
}