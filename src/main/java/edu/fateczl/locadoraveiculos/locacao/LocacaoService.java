package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.endereco.EnderecoDTO;
import edu.fateczl.locadoraveiculos.endereco.EnderecoService;
import edu.fateczl.locadoraveiculos.enums.StatusLocacao;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.locatario.LocatarioDTO;
import edu.fateczl.locadoraveiculos.locatario.LocatarioService;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import io.github.andrelamego.brValidator.cpf.CpfValidationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LocacaoService {

    private final CpfValidationService cpfValidationService = new CpfValidationService();
    private final LocacaoRepository repository;
    private final VeiculoRepository veiculoRepository;
    private final LocacaoMapper mapper;
    private final EnderecoService enderecoService;
    private final LocatarioService locatarioService;

    LocacaoService(
            LocacaoRepository repository,
            VeiculoRepository veiculoRepository,
            LocacaoMapper mapper,
            EnderecoService enderecoService,
            LocatarioService locatarioService
    ) {
        this.repository = repository;
        this.veiculoRepository = veiculoRepository;
        this.mapper = mapper;
        this.enderecoService = enderecoService;
        this.locatarioService = locatarioService;
    }

    public List<LocacaoDTO> listarTodas() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public LocacaoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Locacao nao encontrada."));
    }

    public List<LocacaoDTO> buscarHistoricoCliente(String cpf) {
        return repository.buscarHistoricoCliente(cpf).stream().map(mapper::toDTO).toList();
    }

    public Optional<LocatarioDTO> buscarLocatarioPorCpf(String cpf) {
        return locatarioService.buscarOpcionalPorCpf(normalizarCpfValido(cpf));
    }

    @Transactional
    public LocacaoDTO registrar(LocacaoDTO dto) {
        Veiculo veiculo = veiculoRepository.findById(dto.veiculoPlaca())
                .orElseThrow(() -> new EntityNotFoundException("Veiculo nao encontrado."));

        if (veiculo.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Veiculo indisponivel para locacao.");
        }

        Locacao locacao = mapper.toEntity(dto);
        locacao.setStatus(StatusLocacao.ATIVA);
        veiculo.setStatus(StatusVeiculo.ALUGADO);
        veiculoRepository.save(veiculo);
        return mapper.toDTO(repository.save(locacao));
    }

    @Transactional
    public LocacaoDTO confirmarLocacao(String placa, String cpf, LocalDate dataRetirada, Integer quantidadeDias) {
        validarDadosLocacao(dataRetirada, quantidadeDias);
        return registrarLocacao(placa, normalizarCpfValido(cpf), dataRetirada, quantidadeDias);
    }

    @Transactional
    public LocacaoDTO cadastrarLocatarioEConfirmar(CadastroLocacaoDTO dto) {
        validarDadosLocacao(dto.dataRetirada(), dto.quantidadeDias());
        String cpfFormatado = normalizarCpfValido(dto.cpf());

        EnderecoDTO endereco = enderecoService.salvar(
                new EnderecoDTO(null, dto.logradouro(), dto.numero(), dto.cep(), dto.cidade())
        );
        locatarioService.salvar(new LocatarioDTO(
                cpfFormatado,
                dto.nome(),
                dto.numeroHabilitacao(),
                dto.dataNascimento(),
                endereco.id(),
                null
        ));

        return registrarLocacao(dto.placa(), cpfFormatado, dto.dataRetirada(), dto.quantidadeDias());
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    private LocacaoDTO registrarLocacao(String placa, String cpf, LocalDate dataRetirada, Integer quantidadeDias) {
        LocacaoDTO dto = new LocacaoDTO(null, placa, null, cpf, null, dataRetirada, quantidadeDias, null, null);
        return registrar(dto);
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
