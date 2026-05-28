package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.categoria.Categoria;
import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import edu.fateczl.locadoraveiculos.enums.TipoCambio;
import edu.fateczl.locadoraveiculos.enums.TipoCombustivel;
import edu.fateczl.locadoraveiculos.locacao.Locacao;
import edu.fateczl.locadoraveiculos.reparo.Reparo;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// SOLID - SRP: representa apenas a entidade Veículo do domínio
@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "placa")
public class Veiculo {

    @Id
    @Column(length = 10)
    private String placa;

    @Column(nullable = false, length = 80)
    private String marca;

    @Column(nullable = false, length = 80)
    private String modelo;

    @Column(nullable = false, length = 40)
    private String cor;

    @Column(nullable = false)
    private Integer ano;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_combustivel", nullable = false, length = 20)
    private TipoCombustivel tipoCombustivel;

    @Column(nullable = false)
    private Integer quilometragem;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cambio", nullable = false, length = 20)
    private TipoCambio tipoCambio;

    @Column(name = "capacidade_tanque", nullable = false, precision = 6, scale = 2)
    private BigDecimal capacidadeTanque;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusVeiculo status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "veiculo", fetch = FetchType.LAZY)
    private List<Locacao> locacoes = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", fetch = FetchType.LAZY)
    private List<Reparo> reparos = new ArrayList<>();
}