# Locadora de Veiculos

Sistema Java Web para gerenciamento de uma locadora de veiculos em aeroporto.

## Visao Geral

A aplicacao controla o cadastro da frota, categorias, locatarios, enderecos, locacoes, devolucoes e reparos. Tambem permite consultar veiculos disponiveis por categoria e gerar relatorios PDF com JasperReports.

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

## Funcionalidades

- CRUD de categorias.
- CRUD de veiculos.
- CRUD de enderecos.
- CRUD de locatarios.
- Registro e listagem de locacoes.
- Registro e consulta de devolucoes.
- CRUD de reparos.
- Consulta de veiculos disponiveis por categoria.
- Relatorio PDF de veiculos alugados no dia.
- Relatorio PDF de historico de cliente.
- Relatorio PDF de veiculos em reparo no dia.

## Banco de Dados

Os scripts SQL ficam em `src/main/resources/db` e sao executados na inicializacao pelo Spring:

- `01_tabelas_sqlserver.sql`: tabelas, chaves e constraints.
- `02_views_sqlserver.sql`: views auxiliares.
- `03_functions_sqlserver.sql`: UDFs de consulta.
- `04_procedures_sqlserver.sql`: procedure com cursor para veiculos disponiveis.
- `05_indexes_sqlserver.sql`: indices e restricoes auxiliares.
- `06_data_sqlserver.sql`: dados iniciais.

Configure a conexao em `src/main/resources/application.properties`.

## Relatorios

Os layouts Jasper ficam em `src/main/resources/reports`:

- `veiculos-alugados-dia.jrxml`
- `historico-cliente.jrxml`
- `reparos-dia.jrxml`

As rotas ficam em `/relatorios`.

## Documentacao

A documentacao resumida e os diagramas solicitados estao na pasta `doc`:

- `doc/documentacao-resumida.md`
- `doc/diagrama-classes.mmd`
- `doc/diagrama-er.mmd`

A documentacao completa original tambem esta em `docs/documentacao_locadora_veiculos.md`.

## Como Executar

1. Configure o SQL Server e crie o banco `locadora`.
2. Ajuste usuario, senha e porta em `application.properties`.
3. Execute:

```bash
mvn spring-boot:run
```

4. Acesse:

```text
http://localhost:8080
```

## Testes

Para executar a suite:

```bash
mvn test
```

Os testes cobrem regras de negocio de locacao, devolucao, reparo, CRUDs principais, compilacao dos JRXMLs e rastreabilidade dos requisitos funcionais.
