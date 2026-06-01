package edu.fateczl.locadoraveiculos.locatario;

import edu.fateczl.locadoraveiculos.enums.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// ============================================================
// SOLID - SRP: cada Service concentra apenas regras de negócio
//              da sua respectiva entidade
// SOLID - DIP: Services dependem de abstrações (interfaces de Repository e Mapper)
// ============================================================


@Service
public class LocatarioService {

    private final LocatarioRepository repository;
    private final LocatarioMapper mapper;

    LocatarioService(LocatarioRepository repository, LocatarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<LocatarioDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public LocatarioDTO buscarPorCpf(String cpf) {
        return repository.findById(cpf)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado."));
    }

    public Optional<LocatarioDTO> buscarOpcionalPorCpf(String cpf) {
        return repository.findById(cpf).map(mapper::toDTO);
    }

    @Transactional
    public LocatarioDTO salvar(LocatarioDTO dto) {
        if (!repository.existsById(dto.cpf()) && repository.existsByNumeroHabilitacao(dto.numeroHabilitacao())) {
            throw new IllegalArgumentException("Número de habilitação já cadastrado.");
        }
        Locatario entidade = repository.existsById(dto.cpf())
                ? atualizarExistente(dto)
                : mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entidade));
    }

    private Locatario atualizarExistente(LocatarioDTO dto) {
        Locatario existente = repository.findById(dto.cpf())
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado."));
        mapper.updateFromDTO(dto, existente);
        return existente;
    }

    @Transactional
    public void excluir(String cpf) {
        repository.deleteById(cpf);
    }
}
