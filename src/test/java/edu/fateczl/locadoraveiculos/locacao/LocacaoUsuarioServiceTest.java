package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.endereco.EnderecoDTO;
import edu.fateczl.locadoraveiculos.endereco.EnderecoService;
import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocacaoUsuarioServiceTest {

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private LocatarioService locatarioService;

    @Mock
    private LocacaoService locacaoService;

    private LocacaoUsuarioService service;

    @BeforeEach
    void setUp() {
        service = new LocacaoUsuarioService(enderecoService, locatarioService, locacaoService);
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
        LocacaoDTO locacao = locacao();
        when(locacaoService.registrar(any(LocacaoDTO.class))).thenReturn(locacao);

        assertEquals(locacao, service.confirmarLocacao("ABC1D23", "529.982.247-25", LocalDate.of(2026, 6, 1), 3));

        ArgumentCaptor<LocacaoDTO> captor = ArgumentCaptor.forClass(LocacaoDTO.class);
        verify(locacaoService).registrar(captor.capture());
        assertEquals("ABC1D23", captor.getValue().veiculoPlaca());
        assertEquals("529.982.247-25", captor.getValue().locatarioCpf());
        assertEquals(3, captor.getValue().quantidadeDias());
    }

    @Test
    void deveCadastrarLocatarioEConfirmarLocacao() {
        EnderecoDTO endereco = new EnderecoDTO(7L, "Rua das Flores", "100", "01001-000", "Sao Paulo");
        LocacaoDTO locacao = locacao();

        when(enderecoService.salvar(any(EnderecoDTO.class))).thenReturn(endereco);
        when(locacaoService.registrar(any(LocacaoDTO.class))).thenReturn(locacao);

        assertEquals(locacao, service.cadastrarLocatarioEConfirmar(
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

        ArgumentCaptor<LocatarioDTO> locatarioCaptor = ArgumentCaptor.forClass(LocatarioDTO.class);
        verify(locatarioService).salvar(locatarioCaptor.capture());
        assertEquals("529.982.247-25", locatarioCaptor.getValue().cpf());
        assertEquals(7L, locatarioCaptor.getValue().enderecoId());
        verify(locacaoService).registrar(any(LocacaoDTO.class));
    }

    @Test
    void deveFalharComQuantidadeDeDiasInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> service.confirmarLocacao("ABC1D23", "529.982.247-25", LocalDate.of(2026, 6, 1), 0));
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
                null
        );
    }
}
