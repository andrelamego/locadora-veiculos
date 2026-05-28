package edu.fateczl.locadoraveiculos.locatario;

import edu.fateczl.locadoraveiculos.endereco.Endereco;
import edu.fateczl.locadoraveiculos.locacao.Locacao;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// SOLID - SRP: representa apenas a entidade Locatário do domínio
@Entity
@Table(name = "locatario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public class Locatario {

    @Id
    @Column(length = 14)
    private String cpf;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(name = "numero_habilitacao", nullable = false, unique = true, length = 30)
    private String numeroHabilitacao;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(mappedBy = "locatario", fetch = FetchType.LAZY)
    private List<Locacao> locacoes = new ArrayList<>();
}
