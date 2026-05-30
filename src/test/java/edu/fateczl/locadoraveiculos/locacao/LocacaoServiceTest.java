package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.enums.StatusLocacao;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocacaoServiceTest {

    @Mock
    private LocacaoRepository repository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private LocacaoMapper mapper;

    private LocacaoService service;

    @BeforeEach
    void setUp() {
        service = new LocacaoService(repository, veiculoRepository, mapper);
    }

    @Test
    void deveRegistrarLocacaoAtivaEAlugarVeiculoDisponivel() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1D23");
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);

        LocacaoDTO dto = novaLocacaoDTO();
        Locacao locacao = new Locacao();
        LocacaoDTO esperado = new LocacaoDTO(
                1L,
                "ABC1D23",
                null,
                "111.222.333-44",
                null,
                LocalDate.of(2026, 5, 30),
                3,
                LocalDate.of(2026, 6, 2),
                StatusLocacao.ATIVA
        );

        when(veiculoRepository.findById("ABC1D23")).thenReturn(Optional.of(veiculo));
        when(mapper.toEntity(dto)).thenReturn(locacao);
        when(repository.save(locacao)).thenReturn(locacao);
        when(mapper.toDTO(locacao)).thenReturn(esperado);

        LocacaoDTO resultado = service.registrar(dto);

        assertEquals(StatusLocacao.ATIVA, locacao.getStatus());
        assertEquals(StatusVeiculo.ALUGADO, veiculo.getStatus());
        assertEquals(esperado, resultado);
        verify(veiculoRepository).save(veiculo);
        verify(repository).save(locacao);
    }

    @Test
    void deveImpedirLocacaoDeVeiculoIndisponivel() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1D23");
        veiculo.setStatus(StatusVeiculo.ALUGADO);

        when(veiculoRepository.findById("ABC1D23")).thenReturn(Optional.of(veiculo));

        assertThrows(IllegalStateException.class, () -> service.registrar(novaLocacaoDTO()));

        verify(mapper, never()).toEntity(any());
        verify(repository, never()).save(any());
        verify(veiculoRepository, never()).save(any());
    }

    private LocacaoDTO novaLocacaoDTO() {
        return new LocacaoDTO(
                null,
                "ABC1D23",
                null,
                "111.222.333-44",
                null,
                LocalDate.of(2026, 5, 30),
                3,
                null,
                null
        );
    }
}
