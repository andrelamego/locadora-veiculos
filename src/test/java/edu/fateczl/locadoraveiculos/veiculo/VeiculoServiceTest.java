package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.enums.TipoCambio;
import edu.fateczl.locadoraveiculos.enums.TipoCombustivel;
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
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @Mock
    private VeiculoMapper mapper;

    private VeiculoService service;

    @BeforeEach
    void setUp() {
        service = new VeiculoService(repository, mapper);
    }

    @Test
    void deveListarVeiculos() {
        Veiculo veiculo = new Veiculo();
        VeiculoDTO dto = dto();

        when(repository.findAll()).thenReturn(List.of(veiculo));
        when(mapper.toDTO(veiculo)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarTodos());
    }

    @Test
    void deveConsultarDisponiveisPorCategoria() {
        Veiculo veiculo = new Veiculo();
        VeiculoDTO dto = dto();

        when(repository.listarDisponiveisPorCategoria(1L)).thenReturn(List.of(veiculo));
        when(mapper.toDTO(veiculo)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarDisponiveisPorCategoria(1L));
    }

    @Test
    void deveListarTodosVeiculosDisponiveis() {
        Veiculo veiculo = new Veiculo();
        VeiculoDTO dto = dto();

        when(repository.listarPorStatus(StatusVeiculo.DISPONIVEL)).thenReturn(List.of(veiculo));
        when(mapper.toDTO(veiculo)).thenReturn(dto);

        assertEquals(List.of(dto), service.listarDisponiveis());
    }

    @Test
    void deveCadastrarVeiculo() {
        VeiculoDTO dto = dto();
        Veiculo entidade = new Veiculo();

        when(repository.existsById(dto.placa())).thenReturn(false);
        when(mapper.toEntity(dto)).thenReturn(entidade);
        when(repository.save(entidade)).thenReturn(entidade);
        when(mapper.toDTO(entidade)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
    }

    @Test
    void deveAtualizarVeiculoExistente() {
        VeiculoDTO dto = dto();
        Veiculo existente = new Veiculo();

        when(repository.existsById(dto.placa())).thenReturn(true);
        when(repository.findById(dto.placa())).thenReturn(Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);
        when(mapper.toDTO(existente)).thenReturn(dto);

        assertEquals(dto, service.salvar(dto));
        verify(mapper).updateFromDTO(dto, existente);
    }

    @Test
    void deveFalharAoBuscarVeiculoInexistente() {
        when(repository.findById("ABC1D23")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.buscarPorPlaca("ABC1D23"));
    }

    private VeiculoDTO dto() {
        return new VeiculoDTO(
                "ABC1D23",
                "Fiat",
                "Argo",
                "Prata",
                2023,
                TipoCombustivel.GASOLINA,
                10000,
                TipoCambio.MANUAL,
                new BigDecimal("48.00"),
                StatusVeiculo.DISPONIVEL,
                1L,
                "Economico",
                new BigDecimal("120.00")
        );
    }
}
