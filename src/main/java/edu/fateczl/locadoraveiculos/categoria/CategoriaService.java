package edu.fateczl.locadoraveiculos.categoria;

import edu.fateczl.locadoraveiculos.enums.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    CategoriaService(CategoriaRepository repository, CategoriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<CategoriaDTO> listarTodas() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public CategoriaDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
    }

    @Transactional
    public CategoriaDTO salvar(CategoriaDTO dto) {
        Categoria entidade = dto.id() == null
                ? mapper.toEntity(dto)
                : atualizarExistente(dto);
        return mapper.toDTO(repository.save(entidade));
    }

    private Categoria atualizarExistente(CategoriaDTO dto) {
        Categoria existente = repository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada."));
        mapper.updateFromDTO(dto, existente);
        return existente;
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
