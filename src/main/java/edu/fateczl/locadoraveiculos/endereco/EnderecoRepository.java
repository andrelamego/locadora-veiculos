package edu.fateczl.locadoraveiculos.endereco;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



// SOLID - SRP: cada repository é responsável apenas pelo acesso a dados de uma entidade

@Repository
interface EnderecoRepository extends JpaRepository<Endereco, Long> {}
