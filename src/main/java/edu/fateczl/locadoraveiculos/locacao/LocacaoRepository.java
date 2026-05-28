package edu.fateczl.locadoraveiculos.locacao;

import edu.fateczl.locadoraveiculos.enums.StatusLocacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {

    // findBy — locações ativas de um locatário
    List<Locacao> findByLocatarioCpfAndStatus(String cpf, StatusLocacao status);

    // JPQL — histórico de locações de um cliente ordenado por data
    @Query("SELECT l FROM Locacao l WHERE l.locatario.cpf = :cpf ORDER BY l.dataRetirada DESC")
    List<Locacao> buscarHistoricoCliente(@Param("cpf") String cpf);

    // Nativa — RF-10: veículos alugados no dia via UDF do banco
    @Query(value = "SELECT * FROM dbo.fn_veiculos_alugados_no_dia(:data)", nativeQuery = true)
    List<Object[]> buscarVeiculosAlugadosNoDia(@Param("data") LocalDate data);
}
