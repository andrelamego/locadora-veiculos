USE locadora;
GO

CREATE OR ALTER VIEW dbo.vw_locacoes_ativas AS
SELECT
    l.id,
    l.veiculo_placa,
    l.locatario_cpf,
    l.data_retirada,
    l.quantidade_dias,
    DATEADD(DAY, l.quantidade_dias, l.data_retirada) AS data_prevista_devolucao,
    l.status
FROM dbo.locacao l
WHERE l.status = 'ATIVA';
GO

CREATE OR ALTER VIEW dbo.vw_reparos_ativos AS
SELECT
    r.id,
    r.veiculo_placa,
    r.data_entrada,
    r.quantidade_dias,
    DATEADD(DAY, r.quantidade_dias, r.data_entrada) AS data_prevista_saida,
    r.descricao_problema,
    r.valor_reparo,
    r.status
FROM dbo.reparo r
WHERE r.status = 'EM_ANDAMENTO';
GO

CREATE OR ALTER VIEW dbo.vw_historico_cliente AS
SELECT
    lo.cpf,
    lo.nome,
    lo.numero_habilitacao,
    lo.data_nascimento,
    e.logradouro,
    e.numero,
    e.cep,
    e.cidade,
    v.placa,
    v.marca,
    v.modelo,
    v.cor,
    v.ano,
    l.id AS locacao_id,
    l.data_retirada,
    l.quantidade_dias,
    DATEADD(DAY, l.quantidade_dias, l.data_retirada) AS data_prevista_devolucao,
    l.status AS status_locacao,
    d.data_devolucao,
    d.valor_locacao,
    d.valor_combustivel,
    d.valor_total
FROM dbo.locatario lo
INNER JOIN dbo.endereco e ON e.id = lo.endereco_id
INNER JOIN dbo.locacao l ON l.locatario_cpf = lo.cpf
INNER JOIN dbo.veiculo v ON v.placa = l.veiculo_placa
LEFT JOIN dbo.devolucao d ON d.locacao_id = l.id;
GO
