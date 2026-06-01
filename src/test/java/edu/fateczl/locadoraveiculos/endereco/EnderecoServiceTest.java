package edu.fateczl.locadoraveiculos.endereco;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository repository;

    @Mock
    private EnderecoMapper mapper;

    private EnderecoService service;

    @BeforeEach
    void setUp() {
        service = new EnderecoService(repository, mapper);
    }

    @Test
    void deveListarEnderecos() {
        Endereco endereco = new Endereco();
        EnderecoDTO dto = dto(1L);

        when(repository.findAll()).thenReturn(List.of(endereco));
        when(mapper.toDTO(endereco)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarTodos());
    }

    @Test
    void deveCadastrarEndereco() {
        EnderecoDTO dto = dto(null);
        Endereco entidade = new Endereco();
        EnderecoDTO esperado = dto(1L);

        when(mapper.toEntity(dto)).thenReturn(entidade);
        when(repository.save(entidade)).thenReturn(entidade);
        when(mapper.toDTO(entidade)).thenReturn(esperado);

        assertEquals(esperado, service.salvar(dto));
    }

    @Test
    void deveAtualizarEnderecoExistente() {
        EnderecoDTO dto = dto(1L);
        Endereco existente = new Endereco();

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toDTO(existente)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
        verify(mapper).updateFromDTO(dto, existente);
    }

    @Test
    void deveFalharAoBuscarEnderecoInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorId(99L));
    }

    @Test
    void deveExcluirEndereco() {
        service.excluir(1L);

        verify(repository).deleteById(1L);
    }

    private EnderecoDTO dto(Long id) {
        return new EnderecoDTO(id, "Rua A", "100", "01001-000", "Sao Paulo");
    }
}
