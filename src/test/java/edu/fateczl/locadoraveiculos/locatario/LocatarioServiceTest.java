package edu.fateczl.locadoraveiculos.locatario;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocatarioServiceTest {

    @Mock
    private LocatarioRepository repository;

    @Mock
    private LocatarioMapper mapper;

    private LocatarioService service;

    @BeforeEach
    void setUp() {
        service = new LocatarioService(repository, mapper);
    }

    @Test
    void deveListarLocatarios() {
        Locatario locatario = new Locatario();
        LocatarioDTO dto = dto();

        when(repository.findAll()).thenReturn(List.of(locatario));
        when(mapper.toDTO(locatario)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarTodos());
    }

    @Test
    void deveCadastrarLocatarioComHabilitacaoUnica() {
        LocatarioDTO dto = dto();
        Locatario entidade = new Locatario();

        when(repository.existsById(dto.cpf())).thenReturn(false);
        when(repository.existsByNumeroHabilitacao(dto.numeroHabilitacao())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(entidade);
        when(repository.save(entidade)).thenReturn(entidade);
        when(mapper.toDTO(entidade)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
    }

    @Test
    void deveImpedirHabilitacaoDuplicadaParaNovoLocatario() {
        LocatarioDTO dto = dto();

        when(repository.existsById(dto.cpf())).thenReturn(false);
        when(repository.existsByNumeroHabilitacao(dto.numeroHabilitacao())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void deveAtualizarLocatarioExistente() {
        LocatarioDTO dto = dto();
        Locatario existente = new Locatario();

        when(repository.existsById(dto.cpf())).thenReturn(true);
        when(repository.findById(dto.cpf())).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toDTO(existente)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
        verify(mapper).updateFromDTO(dto, existente);
    }

    @Test
    void deveFalharAoBuscarLocatarioInexistente() {
        when(repository.findById("000.000.000-00")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorCpf("000.000.000-00"));
    }

    private LocatarioDTO dto() {
        return new LocatarioDTO(
                "582.349.761-09",
                "Ana Martins",
                "CNH111222333",
                LocalDate.of(1992, 4, 12),
                1L,
                "Rua A, 100"
        );
    }
}
