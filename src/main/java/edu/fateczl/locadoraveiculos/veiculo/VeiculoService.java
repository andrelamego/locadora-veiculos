package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.enums.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// ============================================================
// SOLID - SRP: cada Service concentra apenas regras de negócio
//              da sua respectiva entidade
// SOLID - DIP: Services dependem de abstrações (interfaces de Repository e Mapper)
// ============================================================

@Service
public class VeiculoService {

    private final VeiculoRepository repository;
    private final VeiculoMapper mapper;

    VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<VeiculoDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public VeiculoDTO buscarPorPlaca(String placa) {
        return repository.findById(placa)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
    }

    // RF-03: consulta veículos disponíveis por categoria
    public List<VeiculoDTO> listarDisponiveisPorCategoria(Long categoriaId) {
        return repository.findByCategoriaIdAndStatus(categoriaId, StatusVeiculo.DISPONIVEL)
                .stream().map(mapper::toDTO).toList();
    }

    @Transactional
    public VeiculoDTO salvar(VeiculoDTO dto) {
        Veiculo entidade = repository.existsById(dto.placa())
                ? atualizarExistente(dto)
                : mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entidade));
    }

    private Veiculo atualizarExistente(VeiculoDTO dto) {
        Veiculo existente = repository.findById(dto.placa())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));
        mapper.updateFromDTO(dto, existente);
        return existente;
    }

    @Transactional
    public void excluir(String placa) {
        repository.deleteById(placa);
    }
}

