package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.enums.StatusReparo;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: representa apenas a entidade Reparo do domínio
@Entity
@Table(name = "reparo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reparo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veiculo_placa")
    private Veiculo veiculo;

    @Column(name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @Column(name = "quantidade_dias", nullable = false)
    private Integer quantidadeDias;

    @Column(name = "descricao_problema", nullable = false, columnDefinition = "TEXT")
    private String descricaoProblema;

    @Column(name = "valor_reparo", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorReparo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusReparo status;

    // Campo derivado: data prevista de saída
    public LocalDate getDataPrevistaSaida() {
        if (dataEntrada == null || quantidadeDias == null) return null;
        return dataEntrada.plusDays(quantidadeDias);
    }
}