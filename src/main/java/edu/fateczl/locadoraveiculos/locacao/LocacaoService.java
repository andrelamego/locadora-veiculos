package edu.fateczl.locadoraveiculos.locacao;

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
public class LocacaoService {

    private final LocacaoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final LocacaoMapper mapper;

    // SOLID - SRP: esta classe concentra apenas regras de negócio de locação
    LocacaoService(LocacaoRepository repository, VeiculoRepository veiculoRepository, LocacaoMapper mapper) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
        this.mapper = mapper;
    }

    public List<LocacaoDTO> listarTodas() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public LocacaoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Locação não encontrada."));
    }

    public List<LocacaoDTO> buscarHistoricoCliente(String cpf) {
        return repository.buscarHistoricoCliente(cpf).stream().map(mapper::toDTO).toList();
    }

    // RF-06: registra locação e marca veículo como ALUGADO
    @Transactional
    public LocacaoDTO registrar(LocacaoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoPlaca())
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado."));

        // RN-04: impede locação de veículo indisponível
        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Veículo indisponível para locação.");
        }

        Locacao locacao = mapper.toEntity(dto);
        locacao.setStatus(StatusLocacao.ATIVA);
        veiculo.setStatus(StatusVeiculo.ALUGADO);
        veiculoRepository.save(veiculo);
        return mapper.toDTO(repository.save(locacao));
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

