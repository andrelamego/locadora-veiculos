package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.devolucao.Devolucao;
import edu.fateczl.locadoraveiculos.enums.StatusLocacao;
import edu.fateczl.locadoraveiculos.locatario.Locatario;
import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

// SOLID - SRP: representa apenas a entidade Locação do domínio
@Entity
@Table(name = "locacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Locacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veiculo_placa")
    private Veiculo veiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locatario_cpf")
    private Locatario locatario;

    @Column(name = "data_retirada", nullable = false)
    private LocalDate dataRetirada;

    @Column(name = "quantidade_dias", nullable = false)
    private Integer quantidadeDias;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusLocacao status;

    @OneToOne(mappedBy = "locacao", fetch = FetchType.LAZY)
    private Devolucao devolucao;

    // Campo derivado: data prevista de devolução
    public LocalDate getDataPrevistaDevolucao() {
        if (dataRetirada == null || quantidadeDias == null) return null;
        return dataRetirada.plusDays(quantidadeDias);
    }
}