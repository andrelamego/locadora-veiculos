package edu.fateczl.locadoraveiculos.reparo;

import edu.fateczl.locadoraveiculos.enums.StatusReparo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade


@Repository
interface ReparoRepository extends JpaRepository<Reparo, Long> {

    // findBy — reparos de um veículo por status
    List<Reparo> findByVeiculoPlacaAndStatus(String placa, StatusReparo status);

    // Nativa — RF-14: reparos ativos na data via UDF do banco
    @Query(value = "SELECT * FROM dbo.fn_reparos_no_dia(:data)", nativeQuery = true)
    List<Object[]> buscarReparosNoDia(@Param("data") LocalDate data);
}