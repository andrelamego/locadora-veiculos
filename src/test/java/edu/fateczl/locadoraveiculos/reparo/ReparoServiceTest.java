package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.enums.StatusReparo;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReparoServiceTest {

    @Mock
    private ReparoRepository repository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ReparoMapper mapper;

    private ReparoService service;

    @BeforeEach
    void setUp() {
        service = new ReparoService(repository, veiculoRepository, mapper);
    }

    @Test
    void deveRegistrarReparoEmAndamentoEMarcarVeiculoEmReparo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1D23");
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        ReparoDTO dto = novoReparoDTO(null);
        Reparo reparo = new Reparo();
        ReparoDTO esperado = novoReparoDTO(1L);

        when(veiculoRepository.findById("ABC1D23")).thenReturn(Optional.of(veiculo));
        when(mapper.toEntity(dto)).thenReturn(reparo);
        when(repository.save(reparo)).thenReturn(reparo);
        when(mapper.toDTO(reparo)).thenReturn(esperado);

        ReparoDTO resultado = service.salvar(dto);

        assertEquals(StatusReparo.EM_ANDAMENTO, reparo.getStatus());
        assertEquals(veiculo, reparo.getVeiculo());
        assertEquals(StatusVeiculo.EM_REPARO, veiculo.getStatus());
        assertEquals(esperado, resultado);
        verify(veiculoRepository).save(veiculo);
        verify(repository).save(reparo);
    }

    @Test
    void deveLiberarVeiculoAoExcluirReparo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setStatus(StatusVeiculo.EM_REPARO);

        Reparo reparo = new Reparo();
        reparo.setId(1L);
        reparo.setVeiculo(veiculo);

        when(repository.findById(1L)).thenReturn(Optional.of(reparo));

        service.excluir(1L);

        assertEquals(StatusVeiculo.DISPONIVEL, veiculo.getStatus());
        verify(veiculoRepository).save(veiculo);
        verify(repository).deleteById(1L);
    }

    private ReparoDTO novoReparoDTO(Long id) {
        return new ReparoDTO(
                id,
                "ABC1D23",
                null,
                LocalDate.of(2026, 5, 30),
                5,
                null,
                "Troca de pastilhas",
                new BigDecimal("850.00"),
                null
        );
    }
}
