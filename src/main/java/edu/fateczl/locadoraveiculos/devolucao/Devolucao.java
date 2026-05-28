package edu.fateczl.locadoraveiculos.devolucao;

import edu.fateczl.locadoraveiculos.locacao.Locacao;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

// SOLID - SRP: representa apenas a entidade Devolução do domínio
@Entity
@Table(name = "devolucao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Devolucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locacao_id", unique = true)
    private Locacao locacao;

    @Column(name = "data_devolucao", nullable = false)
    private LocalDate dataDevolucao;

    @Column(name = "litros_faltantes", nullable = false, precision = 6, scale = 2)
    private BigDecimal litrosFaltantes;

    @Column(name = "valor_combustivel", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorCombustivel;

    @Column(name = "valor_locacao", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorLocacao;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
}