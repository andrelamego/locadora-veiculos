package edu.fateczl.locadoraveiculos.devolucao;

import edu.fateczl.locadoraveiculos.enums.*;
import edu.fateczl.locadoraveiculos.locacao.Locacao;
import edu.fateczl.locadoraveiculos.locacao.LocacaoRepository;
import edu.fateczl.locadoraveiculos.veiculo.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
class DevolucaoService {

    // RN-07 / RN-08: valores por litro de combustível
    private static final BigDecimal VALOR_LITRO_GASOLINA = new BigDecimal("7.00");
    private static final BigDecimal VALOR_LITRO_ALCOOL = new BigDecimal("5.50");

    private final DevolucaoRepository repository;
    private final LocacaoRepository locacaoRepository;
    private final VeiculoRepository veiculoRepository;
    private final DevolucaoMapper mapper;

    // SOLID - SRP: esta classe concentra apenas regras de cálculo e registro de devolução
    DevolucaoService(DevolucaoRepository repository, LocacaoRepository locacaoRepository,
                     VeiculoRepository veiculoRepository, DevolucaoMapper mapper) {
        this.repository = repository;
        this.locacaoRepository = locacaoRepository;
        this.veiculoRepository = veiculoRepository;
        this.mapper = mapper;
    }

    public List<DevolucaoDTO> listarTodas() {
        return repository.findAll().stream().map(mapper::toDTO).toList();
    }

    public DevolucaoDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Devolução não encontrada."));
    }

    // RF-08 / RF-09: calcula e registra devolução com combustível extra
    @Transactional
    public DevolucaoDTO registrar(Long locacaoId, BigDecimal litrosFaltantes, LocalDate dataDevolucao) {
        Locacao locacao = locacaoRepository.findById(locacaoId)
                .orElseThrow(() -> new EntityNotFoundException("Locação não encontrada."));

        if (locacao.getStatus() != StatusLocacao.ATIVA) {
            throw new IllegalStateException("Locação já finalizada.");
        }
        if (repository.existsByLocacaoId(locacaoId)) {
            throw new IllegalStateException("Já existe devolução para esta locação.");
        }

        BigDecimal valorDiaria = locacao.getVeiculo().getCategoria().getValorDiaria();
        BigDecimal valorLocacao = valorDiaria.multiply(BigDecimal.valueOf(locacao.getQuantidadeDias()));
        BigDecimal valorLitro = locacao.getVeiculo().getTipoCombustivel() == TipoCombustivel.GASOLINA
                ? VALOR_LITRO_GASOLINA : VALOR_LITRO_ALCOOL;
        BigDecimal valorCombustivel = litrosFaltantes.multiply(valorLitro);
        BigDecimal valorTotal = valorLocacao.add(valorCombustivel);

        Devolucao devolucao = new Devolucao();
        devolucao.setLocacao(locacao);
        devolucao.setDataDevolucao(dataDevolucao);
        devolucao.setLitrosFaltantes(litrosFaltantes);
        devolucao.setValorCombustivel(valorCombustivel);
        devolucao.setValorLocacao(valorLocacao);
        devolucao.setValorTotal(valorTotal);

        // RN-10: finaliza locação e libera veículo
        locacao.setStatus(StatusLocacao.FINALIZADA);
        locacao.getVeiculo().setStatus(StatusVeiculo.DISPONIVEL);
        locacaoRepository.save(locacao);
        veiculoRepository.save(locacao.getVeiculo());

        return mapper.toDTO(repository.save(devolucao));
    }
}
