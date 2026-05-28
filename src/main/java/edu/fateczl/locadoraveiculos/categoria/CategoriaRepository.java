package edu.fateczl.locadoraveiculos.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade

@Repository
interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
