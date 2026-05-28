package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.enums.*;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
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
class ReparoService {

    private final ReparoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final ReparoMapper mapper;

    // SOLID - SRP: esta classe concentra apenas regras de negócio de reparo
    ReparoService(ReparoRepository repository, VeiculoRepository veiculoRepository, ReparoMapper mapper) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
        this.mapper = mapper;
    }

    public List<ReparoDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public ReparoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reparo não encontrado."));
    }

    // RF-12: registra reparo e marca veículo como EM_REPARO
    @Transactional
    public ReparoDTO salvar(ReparoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoPlaca())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));

        Reparo reparo;
        if (dto.id() == null) {
            reparo = mapper.toEntity(dto);
            reparo.setVeiculo(veiculo);
            reparo.setStatus(StatusReparo.EM_ANDAMENTO);
            veiculo.setStatus(StatusVeiculo.EM_REPARO);
            veiculoRepository.save(veiculo);
        } else {
            reparo = repository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Reparo não encontrado."));
            mapper.updateFromDTO(dto, reparo);
        }

        return mapper.toDTO(repository.save(reparo));
    }

    @Transactional
    public void excluir(Long id) {
        Reparo reparo = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reparo não encontrado."));
        reparo.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);
        veiculoRepository.save(reparo.getVeiculo());
        repository.deleteById(id);
    }
}