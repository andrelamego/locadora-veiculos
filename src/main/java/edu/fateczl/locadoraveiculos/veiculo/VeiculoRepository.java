package edu.fateczl.locadoraveiculos.veiculo;

import edu.fateczl.locadoraveiculos.enums.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, String> {

    // findBy — RF-03: consulta veículos disponíveis por categoria e status
    List<Veiculo> findByCategoriaIdAndStatus(Long categoriaId, StatusVeiculo status);

    // JPQL — lista todos veículos por status
    @Query("SELECT v FROM Veiculo v WHERE v.status = :status ORDER BY v.marca, v.modelo")
    List<Veiculo> listarPorStatus(@Param("status") StatusVeiculo status);
}
