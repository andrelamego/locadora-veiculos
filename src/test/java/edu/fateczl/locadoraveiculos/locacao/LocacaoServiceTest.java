package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.endereco.EnderecoDTO;
import edu.fateczl.locadoraveiculos.endereco.EnderecoService;
import edu.fateczl.locadoraveiculos.enums.StatusLocacao;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private LocatarioService locatarioService;

    private LocacaoService service;

    @BeforeEach
    void setUp() {
        service = new LocacaoService(repository, veiculoRepository, mapper, enderecoService, locatarioService);
    }

    @Test
    void deveRegistrarLocacaoAtivaEAlugarVeiculoDisponivel() {
        Veiculo veiculo = veiculoDisponivel();

        LocacaoDTO dto = novaLocacaoDTO();
        Locacao locacao = new Locacao();
        LocacaoDTO esperado = locacao();

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

    @Test
    void deveBuscarLocatarioPorCpf() {
        LocatarioDTO locatario = locatario();
        when(locatarioService.buscarOpcionalPorCpf(locatario.cpf())).thenReturn(Optional.of(locatario));

        assertEquals(Optional.of(locatario), service.buscarLocatarioPorCpf(locatario.cpf()));
    }

    @Test
    void deveNormalizarCpfValidoAntesDaBusca() {
        LocatarioDTO locatario = locatario();
        when(locatarioService.buscarOpcionalPorCpf(locatario.cpf())).thenReturn(Optional.of(locatario));

        assertEquals(Optional.of(locatario), service.buscarLocatarioPorCpf("52998224725"));
    }

    @Test
    void deveFalharAoBuscarComCpfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> service.buscarLocatarioPorCpf("111.111.111-11"));
    }

    @Test
    void deveConfirmarLocacaoParaLocatarioExistente() {
        LocacaoDTO esperado = prepararRegistroDeLocacao();

        LocacaoDTO resultado = service.confirmarLocacao("ABC1D23", "529.982.247-25", LocalDate.of(2026, 6, 1), 3);

        assertEquals(esperado, resultado);
        ArgumentCaptor<LocacaoDTO> captor = ArgumentCaptor.forClass(LocacaoDTO.class);
        verify(mapper).toEntity(captor.capture());
        assertEquals("ABC1D23", captor.getValue().veiculoPlaca());
        assertEquals("529.982.247-25", captor.getValue().locatarioCpf());
        assertEquals(3, captor.getValue().quantidadeDias());
    }

    @Test
    void deveCadastrarLocatarioEConfirmarLocacao() {
        EnderecoDTO endereco = new EnderecoDTO(7L, "Rua das Flores", "100", "01001-000", "Sao Paulo");
        LocacaoDTO esperado = prepararRegistroDeLocacao();

        when(enderecoService.salvar(any(EnderecoDTO.class))).thenReturn(endereco);

        LocacaoDTO resultado = service.cadastrarLocatarioEConfirmar(new CadastroLocacaoDTO(
                "ABC1D23",
                "529.982.247-25",
                "Ana Silva",
                "CNH123456789",
                LocalDate.of(1990, 5, 10),
                "Rua das Flores",
                "100",
                "01001-000",
                "Sao Paulo",
                LocalDate.of(2026, 6, 1),
                3
        ));

        assertEquals(esperado, resultado);
        ArgumentCaptor<LocatarioDTO> locatarioCaptor = ArgumentCaptor.forClass(LocatarioDTO.class);
        verify(locatarioService).salvar(locatarioCaptor.capture());
        assertEquals("529.982.247-25", locatarioCaptor.getValue().cpf());
        assertEquals(7L, locatarioCaptor.getValue().enderecoId());
    }

    @Test
    void deveFalharComQuantidadeDeDiasInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> service.confirmarLocacao("ABC1D23", "529.982.247-25", LocalDate.of(2026, 6, 1), 0));
    }

    private LocacaoDTO prepararRegistroDeLocacao() {
        Veiculo veiculo = veiculoDisponivel();
        Locacao locacao = new Locacao();
        LocacaoDTO esperado = locacao();

        when(veiculoRepository.findById("ABC1D23")).thenReturn(Optional.of(veiculo));
        when(mapper.toEntity(any(LocacaoDTO.class))).thenReturn(locacao);
        when(repository.save(locacao)).thenReturn(locacao);
        when(mapper.toDTO(locacao)).thenReturn(esperado);

        return esperado;
    }

    private Veiculo veiculoDisponivel() {
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("ABC1D23");
        veiculo.setStatus(StatusVeiculo.DISPONIVEL);
        return veiculo;
    }

    private LocatarioDTO locatario() {
        return new LocatarioDTO(
                "529.982.247-25",
                "Ana Silva",
                "CNH123456789",
                LocalDate.of(1990, 5, 10),
                7L,
                "Rua das Flores, 100 - Sao Paulo"
        );
    }

    private LocacaoDTO locacao() {
        return new LocacaoDTO(
                1L,
                "ABC1D23",
                "Argo",
                "529.982.247-25",
                "Ana Silva",
                LocalDate.of(2026, 6, 1),
                3,
                LocalDate.of(2026, 6, 4),
                StatusLocacao.ATIVA
        );
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
