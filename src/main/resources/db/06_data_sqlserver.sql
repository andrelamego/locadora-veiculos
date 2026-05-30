USE locadora;
GO

SET XACT_ABORT ON;
BEGIN TRANSACTION;

IF NOT EXISTS (SELECT 1 FROM dbo.categoria WHERE nome = 'Economico')
    INSERT INTO dbo.categoria (nome, descricao, valor_diaria)
    VALUES ('Economico', 'Veiculos compactos para uso urbano', 120.00);

IF NOT EXISTS (SELECT 1 FROM dbo.categoria WHERE nome = 'SUV')
    INSERT INTO dbo.categoria (nome, descricao, valor_diaria)
    VALUES ('SUV', 'Veiculos maiores para familias e bagagens', 220.00);

IF NOT EXISTS (SELECT 1 FROM dbo.categoria WHERE nome = 'Luxo')
    INSERT INTO dbo.categoria (nome, descricao, valor_diaria)
    VALUES ('Luxo', 'Veiculos premium para viagens executivas', 380.00);

IF NOT EXISTS (SELECT 1 FROM dbo.endereco WHERE cep = '01001-000' AND numero = '100')
    INSERT INTO dbo.endereco (logradouro, numero, cep, cidade)
    VALUES ('Praca da Se', '100', '01001-000', 'Sao Paulo');

IF NOT EXISTS (SELECT 1 FROM dbo.endereco WHERE cep = '04094-050' AND numero = '200')
    INSERT INTO dbo.endereco (logradouro, numero, cep, cidade)
    VALUES ('Avenida Pedro Alvares Cabral', '200', '04094-050', 'Sao Paulo');

IF NOT EXISTS (SELECT 1 FROM dbo.endereco WHERE cep = '13010-001' AND numero = '300')
    INSERT INTO dbo.endereco (logradouro, numero, cep, cidade)
    VALUES ('Rua Barao de Jaguara', '300', '13010-001', 'Campinas');

DECLARE @enderecoAna BIGINT;
DECLARE @enderecoBruno BIGINT;
DECLARE @enderecoCarla BIGINT;

SELECT TOP 1 @enderecoAna = id
FROM dbo.endereco
WHERE cep = '01001-000' AND numero = '100'
ORDER BY id;

SELECT TOP 1 @enderecoBruno = id
FROM dbo.endereco
WHERE cep = '04094-050' AND numero = '200'
ORDER BY id;

SELECT TOP 1 @enderecoCarla = id
FROM dbo.endereco
WHERE cep = '13010-001' AND numero = '300'
ORDER BY id;

IF NOT EXISTS (SELECT 1 FROM dbo.locatario WHERE cpf = '111.222.333-44')
    INSERT INTO dbo.locatario (cpf, nome, numero_habilitacao, data_nascimento, endereco_id)
    VALUES ('111.222.333-44', 'Ana Martins', 'CNH111222333', '1992-04-12', @enderecoAna);

IF NOT EXISTS (SELECT 1 FROM dbo.locatario WHERE cpf = '222.333.444-55')
    INSERT INTO dbo.locatario (cpf, nome, numero_habilitacao, data_nascimento, endereco_id)
    VALUES ('222.333.444-55', 'Bruno Almeida', 'CNH222333444', '1987-09-23', @enderecoBruno);

IF NOT EXISTS (SELECT 1 FROM dbo.locatario WHERE cpf = '333.444.555-66')
    INSERT INTO dbo.locatario (cpf, nome, numero_habilitacao, data_nascimento, endereco_id)
    VALUES ('333.444.555-66', 'Carla Souza', 'CNH333444555', '1995-01-30', @enderecoCarla);

DECLARE @categoriaEconomico BIGINT;
DECLARE @categoriaSuv BIGINT;
DECLARE @categoriaLuxo BIGINT;

SELECT @categoriaEconomico = id FROM dbo.categoria WHERE nome = 'Economico';
SELECT @categoriaSuv = id FROM dbo.categoria WHERE nome = 'SUV';
SELECT @categoriaLuxo = id FROM dbo.categoria WHERE nome = 'Luxo';

IF NOT EXISTS (SELECT 1 FROM dbo.veiculo WHERE placa = 'ABC1D23')
    INSERT INTO dbo.veiculo (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        'ABC1D23', 'Fiat', 'Argo', 'Prata', 2023, 'GASOLINA', 18500,
        'MANUAL', 48.00, 'ALUGADO', @categoriaEconomico
    );

IF NOT EXISTS (SELECT 1 FROM dbo.veiculo WHERE placa = 'DEF4G56')
    INSERT INTO dbo.veiculo (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        'DEF4G56', 'Hyundai', 'HB20', 'Branco', 2022, 'ALCOOL', 30200,
        'AUTOMATICO', 50.00, 'DISPONIVEL', @categoriaEconomico
    );

IF NOT EXISTS (SELECT 1 FROM dbo.veiculo WHERE placa = 'GHI7J89')
    INSERT INTO dbo.veiculo (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        'GHI7J89', 'Jeep', 'Compass', 'Cinza', 2024, 'GASOLINA', 9500,
        'AUTOMATICO', 60.00, 'EM_REPARO', @categoriaSuv
    );

IF NOT EXISTS (SELECT 1 FROM dbo.veiculo WHERE placa = 'JKL8M90')
    INSERT INTO dbo.veiculo (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        'JKL8M90', 'Toyota', 'Corolla', 'Preto', 2023, 'GASOLINA', 14100,
        'AUTOMATICO', 55.00, 'DISPONIVEL', @categoriaLuxo
    );

IF NOT EXISTS (SELECT 1 FROM dbo.veiculo WHERE placa = 'MNO2P34')
    INSERT INTO dbo.veiculo (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        'MNO2P34', 'Volkswagen', 'T-Cross', 'Azul', 2023, 'ALCOOL', 22000,
        'AUTOMATICO', 52.00, 'DISPONIVEL', @categoriaSuv
    );

IF NOT EXISTS (
    SELECT 1
    FROM dbo.locacao
    WHERE veiculo_placa = 'ABC1D23'
      AND status = 'ATIVA'
)
    INSERT INTO dbo.locacao (veiculo_placa, locatario_cpf, data_retirada, quantidade_dias, status)
    VALUES ('ABC1D23', '111.222.333-44', '2026-05-28', 4, 'ATIVA');

IF NOT EXISTS (
    SELECT 1
    FROM dbo.locacao
    WHERE veiculo_placa = 'DEF4G56'
      AND locatario_cpf = '222.333.444-55'
      AND data_retirada = '2026-05-20'
)
    INSERT INTO dbo.locacao (veiculo_placa, locatario_cpf, data_retirada, quantidade_dias, status)
    VALUES ('DEF4G56', '222.333.444-55', '2026-05-20', 3, 'FINALIZADA');

DECLARE @locacaoFinalizada BIGINT;

SELECT TOP 1 @locacaoFinalizada = id
FROM dbo.locacao
WHERE veiculo_placa = 'DEF4G56'
  AND locatario_cpf = '222.333.444-55'
  AND data_retirada = '2026-05-20'
ORDER BY id;

IF @locacaoFinalizada IS NOT NULL
   AND NOT EXISTS (SELECT 1 FROM dbo.devolucao WHERE locacao_id = @locacaoFinalizada)
    INSERT INTO dbo.devolucao (
        locacao_id,
        data_devolucao,
        litros_faltantes,
        valor_combustivel,
        valor_locacao,
        valor_total
    )
    VALUES (
        @locacaoFinalizada,
        '2026-05-23',
        6.00,
        33.00,
        360.00,
        393.00
    );

IF NOT EXISTS (
    SELECT 1
    FROM dbo.reparo
    WHERE veiculo_placa = 'GHI7J89'
      AND status = 'EM_ANDAMENTO'
)
    INSERT INTO dbo.reparo (
        veiculo_placa,
        data_entrada,
        quantidade_dias,
        descricao_problema,
        valor_reparo,
        status
    )
    VALUES (
        'GHI7J89',
        '2026-05-29',
        5,
        'Troca de pastilhas de freio e revisao preventiva',
        850.00,
        'EM_ANDAMENTO'
    );

UPDATE dbo.veiculo SET status = 'ALUGADO' WHERE placa = 'ABC1D23';
UPDATE dbo.veiculo SET status = 'EM_REPARO' WHERE placa = 'GHI7J89';
UPDATE dbo.veiculo SET status = 'DISPONIVEL' WHERE placa IN ('DEF4G56', 'JKL8M90', 'MNO2P34');

COMMIT TRANSACTION;
GO
