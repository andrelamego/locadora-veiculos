package edu.fateczl.locadoraveiculos.endereco;

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
public class EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoMapper mapper;

    EnderecoService(EnderecoRepository repository, EnderecoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<EnderecoDTO> listarTodos() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public EnderecoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado."));
    }

    @Transactional
    public EnderecoDTO salvar(EnderecoDTO dto) {
        Endereco entidade = dto.id() == null
                ? mapper.toEntity(dto)
                : atualizarExistente(dto);
        return mapper.toDTO(repository.save(entidade));
    }

    private Endereco atualizarExistente(EnderecoDTO dto) {
        Endereco existente = repository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado."));
        mapper.updateFromDTO(dto, existente);
        return existente;
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}

