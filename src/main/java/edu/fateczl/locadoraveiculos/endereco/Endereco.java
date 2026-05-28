package edu.fateczl.locadoraveiculos.endereco;

import jakarta.persistence.*;
import lombok.*;

// SOLID - SRP: representa apenas a entidade Endereço do domínio
@Entity
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length = 10)
    private String cep;

    @Column(nullable = false, length = 80)
    private String cidade;
}
