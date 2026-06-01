package edu.fateczl.locadoraveiculos.categoria;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @Mock
    private CategoriaMapper mapper;

    private CategoriaService service;

    @BeforeEach
    void setUp() {
        service = new CategoriaService(repository, mapper);
    }

    @Test
    void deveListarCategorias() {
        Categoria categoria = new Categoria();
        CategoriaDTO dto = dto(1L);

        when(repository.findAll()).thenReturn(List.of(categoria));
        when(mapper.toDTO(categoria)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarTodas());
    }

    @Test
    void deveCadastrarCategoria() {
        CategoriaDTO dto = dto(null);
        Categoria entidade = new Categoria();
        CategoriaDTO esperado = dto(1L);

        when(mapper.toEntity(dto)).thenReturn(entidade);
        when(repository.save(entidade)).thenReturn(entidade);
        when(mapper.toDTO(entidade)).thenReturn(esperado);

        assertEquals(esperado, service.salvar(dto));
    }

    @Test
    void deveAtualizarCategoriaExistente() {
        CategoriaDTO dto = dto(1L);
        Categoria existente = new Categoria();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toDTO(existente)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
        verify(mapper).updateFromDTO(dto, existente);
    }

    @Test
    void deveFalharAoBuscarCategoriaInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void deveExcluirCategoria() {
        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    private CategoriaDTO dto(Long id) {
        return new CategoriaDTO(id, "Economico", "Compactos", new BigDecimal("120.00"));
    }
}
