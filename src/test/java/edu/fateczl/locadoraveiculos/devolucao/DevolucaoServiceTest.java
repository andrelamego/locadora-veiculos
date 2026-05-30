package edu.fateczl.locadoraveiculos.devolucao;

import edu.fateczl.locadoraveiculos.categoria.Categoria;
import edu.fateczl.locadoraveiculos.enums.StatusLocacao;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.enums.TipoCombustivel;
import edu.fateczl.locadoraveiculos.locacao.Locacao;
import edu.fateczl.locadoraveiculos.locacao.LocacaoRepository;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DevolucaoServiceTest {

    @Mock
    private DevolucaoRepository repository;

    @Mock
    private LocacaoRepository locacaoRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private DevolucaoMapper mapper;

    private DevolucaoService service;

    @BeforeEach
    void setUp() {
        service = new DevolucaoService(repository, locacaoRepository, veiculoRepository, mapper);
    }

    @Test
    void deveCalcularDevolucaoComGasolinaEFinalizarLocacao() {
        Locacao locacao = locacaoAtiva(TipoCombustivel.GASOLINA);
        LocalDate dataDevolucao = LocalDate.of(2026, 6, 2);

        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
        when(repository.existsByLocacaoId(1L)).thenReturn(false);
        when(repository.save(any(Devolucao.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Devolucao.class))).thenReturn(new DevolucaoDTO(
                10L,
                1L,
                "ABC1D23",
                null,
                null,
                dataDevolucao,
                new BigDecimal("5.00"),
                new BigDecimal("35.00"),
                new BigDecimal("300.00"),
                new BigDecimal("335.00")
        ));

        service.registrar(1L, new BigDecimal("5.00"), dataDevolucao);

        ArgumentCaptor<Devolucao> captor = ArgumentCaptor.forClass(Devolucao.class);
        verify(repository).save(captor.capture());
        Devolucao devolucao = captor.getValue();

        assertEquals(new BigDecimal("300.00"), devolucao.getValorLocacao());
        assertEquals(new BigDecimal("35.0000"), devolucao.getValorCombustivel());
        assertEquals(new BigDecimal("335.0000"), devolucao.getValorTotal());
        assertEquals(StatusLocacao.FINALIZADA, locacao.getStatus());
        assertEquals(StatusVeiculo.DISPONIVEL, locacao.getVeiculo().getStatus());
        verify(locacaoRepository).save(locacao);
        verify(veiculoRepository).save(locacao.getVeiculo());
    }

    @Test
    void deveCalcularDevolucaoComAlcool() {
        Locacao locacao = locacaoAtiva(TipoCombustivel.ALCOOL);

        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
        when(repository.existsByLocacaoId(1L)).thenReturn(false);
        when(repository.save(any(Devolucao.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toDTO(any(Devolucao.class))).thenReturn(new DevolucaoDTO(
                10L,
                1L,
                "ABC1D23",
                null,
                null,
                LocalDate.of(2026, 6, 2),
                new BigDecimal("4.00"),
                new BigDecimal("22.00"),
                new BigDecimal("300.00"),
                new BigDecimal("322.00")
        ));

        service.registrar(1L, new BigDecimal("4.00"), LocalDate.of(2026, 6, 2));

        ArgumentCaptor<Devolucao> captor = ArgumentCaptor.forClass(Devolucao.class);
        verify(repository).save(captor.capture());

        assertEquals(new BigDecimal("22.0000"), captor.getValue().getValorCombustivel());
        assertEquals(new BigDecimal("322.0000"), captor.getValue().getValorTotal());
    }

    @Test
    void deveImpedirDevolucaoDeLocacaoFinalizada() {
        Locacao locacao = locacaoAtiva(TipoCombustivel.GASOLINA);
        locacao.setStatus(StatusLocacao.FINALIZADA);

        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));

        assertThrows(IllegalStateException.class,
                () -> service.registrar(1L, BigDecimal.ZERO, LocalDate.of(2026, 6, 2)));

        verify(repository, never()).save(any());
        verify(veiculoRepository, never()).save(any());
    }

    @Test
    void deveImpedirDuasDevolucoesParaMesmaLocacao() {
        Locacao locacao = locacaoAtiva(TipoCombustivel.GASOLINA);

        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
        when(repository.existsByLocacaoId(1L)).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> service.registrar(1L, BigDecimal.ZERO, LocalDate.of(2026, 6, 2)));

        verify(repository, never()).save(any());
        verify(veiculoRepository, never()).save(any());
    }

    private Locacao locacaoAtiva(TipoCombustivel tipoCombustivel) {
        Categoria categoria = new Categoria();
        categoria.setValorDiaria(new BigDecimal("100.00"));

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1D23");
        veiculo.setTipoCombustivel(tipoCombustivel);
        veiculo.setStatus(StatusVeiculo.ALUGADO);
        veiculo.setCategoria(categoria);

        Locacao locacao = new Locacao();
        locacao.setId(1L);
        locacao.setVeiculo(veiculo);
        locacao.setQuantidadeDias(3);
        locacao.setStatus(StatusLocacao.ATIVA);

        return locacao;
    }
}
