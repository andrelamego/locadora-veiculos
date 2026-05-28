package edu.fateczl.locadoraveiculos.categoria;

import edu.fateczl.locadoraveiculos.veiculo.Veiculo;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// SOLID - SRP: representa apenas a entidade Categoria do domínio
@Entity
@Table(name = "categoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(name = "valor_diaria", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private List<Veiculo> veiculos = new ArrayList<>();
}
