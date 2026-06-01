package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.endereco.EnderecoDTO;
import edu.fateczl.locadoraveiculos.endereco.EnderecoService;
import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import io.github.andrelamego.brValidator.cpf.CpfValidationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

// SOLID - SRP: orquestra apenas o fluxo publico de nova locacao pelo cliente.
// SOLID - DIP: depende dos services de dominio, sem conhecer detalhes de persistencia.
@Service
public class LocacaoUsuarioService {

    private final CpfValidationService cpfValidationService = new CpfValidationService();
    private final EnderecoService enderecoService;
    private final LocatarioService locatarioService;
    private final LocacaoService locacaoService;

    public LocacaoUsuarioService(
            EnderecoService enderecoService,
            LocatarioService locatarioService,
            LocacaoService locacaoService
    ) {
        this.enderecoService = enderecoService;
        this.locatarioService = locatarioService;
        this.locacaoService = locacaoService;
    }

    public Optional<LocatarioDTO> buscarLocatarioPorCpf(String cpf) {
        return locatarioService.buscarOpcionalPorCpf(normalizarCpfValido(cpf));
    }

    @Transactional
    public LocacaoDTO confirmarLocacao(String placa, String cpf, LocalDate dataRetirada, Integer quantidadeDias) {
        validarDadosLocacao(dataRetirada, quantidadeDias);
        return registrarLocacao(placa, normalizarCpfValido(cpf), dataRetirada, quantidadeDias);
    }

    @Transactional
    public LocacaoDTO cadastrarLocatarioEConfirmar(
            String placa,
            String cpf,
            String nome,
            String numeroHabilitacao,
            LocalDate dataNascimento,
            String logradouro,
            String numero,
            String cep,
            String cidade,
            LocalDate dataRetirada,
            Integer quantidadeDias
    ) {
        validarDadosLocacao(dataRetirada, quantidadeDias);
        String cpfFormatado = normalizarCpfValido(cpf);

        EnderecoDTO endereco = enderecoService.salvar(new EnderecoDTO(null, logradouro, numero, cep, cidade));
        locatarioService.salvar(new LocatarioDTO(cpfFormatado, nome, numeroHabilitacao, dataNascimento, endereco.id(), null));

        return registrarLocacao(placa, cpfFormatado, dataRetirada, quantidadeDias);
    }

    private LocacaoDTO registrarLocacao(String placa, String cpf, LocalDate dataRetirada, Integer quantidadeDias) {
        LocacaoDTO dto = new LocacaoDTO(null, placa, null, cpf, null, dataRetirada, quantidadeDias, null, null);
        return locacaoService.registrar(dto);
    }

    private void validarDadosLocacao(LocalDate dataRetirada, Integer quantidadeDias) {
        if (dataRetirada == null) {
            throw new IllegalArgumentException("Informe a data de retirada.");
        }
        if (quantidadeDias == null || quantidadeDias < 1) {
            throw new IllegalArgumentException("A quantidade de dias deve ser maior que zero.");
        }
    }

    String normalizarCpfValido(String cpf) {
        if (!cpfValidationService.isValid(cpf)) {
            throw new IllegalArgumentException("CPF invalido. Verifique o numero informado.");
        }
        return cpfValidationService.formatar(cpf);
    }
}
