USE locadora;
GO

CREATE OR ALTER FUNCTION dbo.fn_carros_disponiveis(@categoria_id BIGINT)
RETURNS TABLE
AS
RETURN
(
    SELECT
        v.placa,
        v.marca,
        v.modelo,
        v.cor,
        v.ano,
        v.tipo_combustivel,
        v.quilometragem,
        v.tipo_cambio,
        v.capacidade_tanque,
        v.status,
        v.categoria_id,
        c.nome AS categoria,
        c.valor_diaria
    FROM dbo.veiculo v
    INNER JOIN dbo.categoria c ON c.id = v.categoria_id
    WHERE v.categoria_id = @categoria_id
      AND v.status = 'DISPONIVEL'
      AND NOT EXISTS (
          SELECT 1
          FROM dbo.locacao l
          WHERE l.veiculo_placa = v.placa
            AND l.status = 'ATIVA'
      )
      AND NOT EXISTS (
          SELECT 1
          FROM dbo.reparo r
          WHERE r.veiculo_placa = v.placa
            AND r.status = 'EM_ANDAMENTO'
      )
);
GO

CREATE OR ALTER FUNCTION dbo.fn_reparos_no_dia(@data DATE)
RETURNS TABLE
AS
RETURN
(
    SELECT
        r.id AS reparo_id,
        v.placa,
        v.marca,
        v.modelo,
        v.cor,
        v.ano,
        r.data_entrada,
        r.quantidade_dias,
        DATEADD(DAY, r.quantidade_dias, r.data_entrada) AS data_prevista_saida,
        r.descricao_problema,
        r.valor_reparo,
        r.status
    FROM dbo.reparo r
    INNER JOIN dbo.veiculo v ON v.placa = r.veiculo_placa
    WHERE r.status = 'EM_ANDAMENTO'
      AND @data BETWEEN r.data_entrada AND DATEADD(DAY, r.quantidade_dias, r.data_entrada)
);
GO

CREATE OR ALTER FUNCTION dbo.fn_veiculos_alugados_no_dia(@data DATE)
RETURNS TABLE
AS
RETURN
(
    SELECT
        l.id AS locacao_id,
        v.placa,
        v.marca,
        v.modelo,
        v.cor,
        v.ano,
        lo.cpf,
        lo.nome,
        l.data_retirada,
        l.quantidade_dias,
        DATEADD(DAY, l.quantidade_dias, l.data_retirada) AS data_prevista_devolucao
    FROM dbo.locacao l
    INNER JOIN dbo.veiculo v ON v.placa = l.veiculo_placa
    INNER JOIN dbo.locatario lo ON lo.cpf = l.locatario_cpf
    WHERE l.status = 'ATIVA'
      AND @data BETWEEN l.data_retirada AND DATEADD(DAY, l.quantidade_dias, l.data_retirada)
);
GO

CREATE OR ALTER FUNCTION dbo.fn_historico_cliente(@cpf VARCHAR(14))
RETURNS TABLE
AS
RETURN
(
    SELECT
        h.cpf,
        h.nome,
        h.numero_habilitacao,
        h.data_nascimento,
        h.logradouro,
        h.numero,
        h.cep,
        h.cidade,
        h.placa,
        h.marca,
        h.modelo,
        h.cor,
        h.ano,
        h.locacao_id,
        h.data_retirada,
        h.quantidade_dias,
        h.data_prevista_devolucao,
        h.status_locacao,
        h.data_devolucao,
        h.valor_locacao,
        h.valor_combustivel,
        h.valor_total
    FROM dbo.vw_historico_cliente h
    WHERE h.cpf = @cpf
);
GO
