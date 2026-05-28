package edu.fateczl.locadoraveiculos.devolucao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade


@Repository
interface DevolucaoRepository extends JpaRepository<Devolucao, Long> {
    boolean existsByLocacaoId(Long locacaoId);
}

