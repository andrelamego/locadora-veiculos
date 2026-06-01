# Sistema de Locadora de Veiculos

## Visao Geral

O sistema gerencia as operacoes de uma locadora de veiculos localizada em um aeroporto. A aplicacao permite cadastrar veiculos, categorias, locatarios, enderecos, locacoes, devolucoes e reparos, alem de consultar disponibilidade da frota e gerar relatorios em PDF.

A solucao foi desenvolvida em Java Web com Spring Boot, Spring Web MVC, Spring Data JPA, JSP/JSTL, SQL Server e JasperReports.

## Objetivos

O objetivo principal e controlar o ciclo operacional da locadora: cadastro da frota, aluguel dos automoveis, devolucao com calculo de valores, manutencao dos veiculos e emissao de relatorios.

Objetivos especificos:

- manter o cadastro de veiculos e suas categorias;
- consultar veiculos disponiveis por categoria;
- registrar locatarios e seus enderecos;
- registrar locacoes com data de retirada e quantidade de dias;
- registrar devolucoes e calcular o valor total a pagar;
- aplicar cobranca por combustivel faltante;
- registrar reparos e indisponibilizar veiculos em manutencao;
- gerar relatorios PDF para acompanhamento da operacao.

## Escopo

- CRUD de categoria, veiculo, endereco, locatario, locacao, devolucao e reparo;
- consulta de veiculos disponiveis por categoria;
- controle de indisponibilidade por locacao ativa ou reparo em andamento;
- calculo do valor da devolucao;
- scripts SQL modularizados;
- relatorios PDF com JasperReports;
- telas JSP com CSS e Bootstrap Icons.

## Requisitos Funcionais

- RF01: cadastrar, listar, editar e excluir categorias.
- RF02: cadastrar, listar, editar e excluir veiculos.
- RF03: consultar veiculos disponiveis por categoria.
- RF04: cadastrar, listar, editar e excluir locatarios.
- RF05: cadastrar, listar, editar e excluir enderecos.
- RF06: registrar locacoes.
- RF07: registrar devolucoes.
- RF08: calcular valor da locacao e combustivel faltante.
- RF09: cadastrar, listar, editar e excluir reparos.
- RF10: consultar veiculos alugados no dia.
- RF11: gerar relatorio PDF de veiculos alugados no dia.
- RF12: gerar relatorio PDF de historico de cliente.
- RF13: gerar relatorio PDF de veiculos em reparo no dia.
- RF14: disponibilizar as consultas pela camada View e tratar as requisicoes nos Controllers.

## Requisitos Nao Funcionais

- RNF01: utilizar Spring Boot, Spring Web MVC e Spring Data JPA.
- RNF02: utilizar SQL Server como banco de dados.
- RNF03: manter separacao em camadas: Controller, Service, Repository, Model, DTO/Mapper e View.
- RNF04: aplicar principios SOLID com comentarios no codigo.
- RNF05: usar CSS para melhorar a usabilidade.
- RNF06: gerar relatorios em PDF com JasperReports.
- RNF07: manter scripts SQL organizados e modularizados.
- RNF08: preservar integridade referencial com chaves estrangeiras e constraints.

## Regras de Negocio Principais

- O veiculo e identificado pela placa.
- O valor da diaria pertence a categoria do veiculo.
- Veiculos alugados ou em reparo sao considerados indisponiveis.
- Uma locacao so pode ser aberta para veiculo disponivel.
- A devolucao finaliza a locacao e libera o veiculo.
- O valor base da locacao e calculado por: quantidade de dias * diaria da categoria.
- O combustivel faltante custa R$ 7,00 por litro para gasolina e R$ 5,50 por litro para alcool.
- Um reparo em andamento deixa o veiculo indisponivel.

## Diagramas

Os diagramas solicitados estao em arquivos separados nesta pasta:

- [Diagrama de Classes](diagrama-classes.mmd)
- [Diagrama Entidade-Relacionamento](diagrama-er.mmd)

## Tecnologias

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- JSP/JSTL
- SQL Server
- JasperReports
- Bootstrap Icons
- Maven
