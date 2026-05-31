USE locadora;
GO

SET XACT_ABORT ON;
BEGIN TRANSACTION;

MERGE dbo.categoria AS destino
USING (VALUES
    ('Economico', 'Veiculos compactos para uso urbano', 120.00),
    ('SUV', 'Veiculos maiores para familias e bagagens', 220.00),
    ('Luxo', 'Veiculos premium para viagens executivas', 380.00),
    ('Minivan', 'Veiculos com mais assentos para familias e grupos', 260.00),
    ('Picape', 'Veiculos utilitarios para estrada e carga leve', 300.00)
) AS origem (nome, descricao, valor_diaria)
ON destino.nome = origem.nome
WHEN NOT MATCHED THEN
    INSERT (nome, descricao, valor_diaria)
    VALUES (origem.nome, origem.descricao, origem.valor_diaria);

MERGE dbo.endereco AS destino
USING (VALUES
    ('Praca da Se', '100', '01001-000', 'Sao Paulo'),
    ('Avenida Pedro Alvares Cabral', '200', '04094-050', 'Sao Paulo'),
    ('Rua Barao de Jaguara', '300', '13010-001', 'Campinas'),
    ('Rua Augusta', '415', '01305-000', 'Sao Paulo'),
    ('Avenida Paulista', '1578', '01310-200', 'Sao Paulo'),
    ('Rua Vergueiro', '920', '01504-001', 'Sao Paulo'),
    ('Avenida Brasil', '840', '13073-001', 'Campinas'),
    ('Rua Quinze de Novembro', '55', '11010-151', 'Santos'),
    ('Avenida Ana Costa', '612', '11060-002', 'Santos'),
    ('Rua Floriano Peixoto', '88', '13400-520', 'Piracicaba'),
    ('Avenida Nove de Julho', '1200', '13208-056', 'Jundiai'),
    ('Rua Campos Sales', '75', '14015-110', 'Ribeirao Preto'),
    ('Avenida Independencia', '909', '13419-155', 'Piracicaba'),
    ('Rua Sao Bento', '240', '01010-001', 'Sao Paulo'),
    ('Avenida Brigadeiro Faria Lima', '3500', '04538-132', 'Sao Paulo')
) AS origem (logradouro, numero, cep, cidade)
ON destino.cep = origem.cep AND destino.numero = origem.numero
WHEN NOT MATCHED THEN
    INSERT (logradouro, numero, cep, cidade)
    VALUES (origem.logradouro, origem.numero, origem.cep, origem.cidade);

DECLARE @cpfMigracao TABLE (
    cpf_antigo VARCHAR(14) NOT NULL,
    cpf_novo VARCHAR(14) NOT NULL,
    numero_habilitacao VARCHAR(30) NOT NULL,
    nome VARCHAR(120) NOT NULL,
    data_nascimento DATE NOT NULL,
    cep VARCHAR(10) NOT NULL,
    numero VARCHAR(20) NOT NULL
);

INSERT INTO @cpfMigracao (
    cpf_antigo,
    cpf_novo,
    numero_habilitacao,
    nome,
    data_nascimento,
    cep,
    numero
)
VALUES
    ('111.222.333-44', '582.349.761-09', 'CNH111222333', 'Ana Martins', '1992-04-12', '01001-000', '100'),
    ('222.333.444-55', '734.920.185-05', 'CNH222333444', 'Bruno Almeida', '1987-09-23', '04094-050', '200'),
    ('333.444.555-66', '291.847.305-79', 'CNH333444555', 'Carla Souza', '1995-01-30', '13010-001', '300'),
    ('444.555.666-77', '846.205.319-60', 'CNH444555666', 'Diego Lima', '1990-06-18', '01305-000', '415'),
    ('555.666.777-88', '105.938.472-88', 'CNH555666777', 'Eduarda Pereira', '1984-11-02', '01310-200', '1578'),
    ('666.777.888-99', '672.841.930-69', 'CNH666777888', 'Fernanda Rocha', '1998-03-21', '01504-001', '920'),
    ('777.888.999-00', '458.120.639-15', 'CNH777888999', 'Gabriel Santos', '1991-12-08', '13073-001', '840'),
    ('888.999.000-11', '309.761.584-93', 'CNH888999000', 'Helena Costa', '1989-07-14', '11010-151', '55'),
    ('999.000.111-22', '917.253.640-34', 'CNH999000111', 'Igor Barros', '1993-10-05', '11060-002', '612'),
    ('123.456.789-00', '640.382.917-31', 'CNH123456789', 'Juliana Alves', '1986-02-27', '13400-520', '88'),
    ('234.567.890-11', '253.709.481-60', 'CNH234567890', 'Karen Dias', '1997-08-09', '13208-056', '1200'),
    ('345.678.901-22', '798.164.205-11', 'CNH345678901', 'Lucas Ribeiro', '1994-05-16', '14015-110', '75'),
    ('456.789.012-33', '120.495.683-98', 'CNH456789012', 'Mariana Nunes', '1988-01-11', '13419-155', '909'),
    ('567.890.123-44', '934.681.720-87', 'CNH567890123', 'Patricia Gomes', '1996-09-19', '01010-001', '240'),
    ('678.901.234-55', '516.270.934-43', 'CNH678901234', 'Roberto Azevedo', '1983-04-25', '04538-132', '3500');

UPDATE locatario
SET numero_habilitacao = CONCAT(locatario.numero_habilitacao, '_OLD')
FROM dbo.locatario locatario
INNER JOIN @cpfMigracao migracao
    ON locatario.cpf = migracao.cpf_antigo
   AND locatario.numero_habilitacao = migracao.numero_habilitacao
WHERE NOT EXISTS (
    SELECT 1
    FROM dbo.locatario existente
    WHERE existente.cpf = migracao.cpf_novo
);

INSERT INTO dbo.locatario (cpf, nome, numero_habilitacao, data_nascimento, endereco_id)
SELECT
    migracao.cpf_novo,
    migracao.nome,
    migracao.numero_habilitacao,
    migracao.data_nascimento,
    (SELECT TOP 1 id FROM dbo.endereco WHERE cep = migracao.cep AND numero = migracao.numero ORDER BY id)
FROM @cpfMigracao migracao
WHERE EXISTS (SELECT 1 FROM dbo.locatario antigo WHERE antigo.cpf = migracao.cpf_antigo)
  AND NOT EXISTS (SELECT 1 FROM dbo.locatario novo WHERE novo.cpf = migracao.cpf_novo);

UPDATE locacao
SET locatario_cpf = migracao.cpf_novo
FROM dbo.locacao locacao
INNER JOIN @cpfMigracao migracao
    ON locacao.locatario_cpf = migracao.cpf_antigo
WHERE EXISTS (
    SELECT 1
    FROM dbo.locatario novo
    WHERE novo.cpf = migracao.cpf_novo
);

DELETE locatario
FROM dbo.locatario locatario
INNER JOIN @cpfMigracao migracao
    ON locatario.cpf = migracao.cpf_antigo
WHERE NOT EXISTS (
    SELECT 1
    FROM dbo.locacao locacao
    WHERE locacao.locatario_cpf = locatario.cpf
);

MERGE dbo.locatario AS destino
USING (VALUES
    ('582.349.761-09', 'Ana Martins', 'CNH111222333', '1992-04-12', '01001-000', '100'),
    ('734.920.185-05', 'Bruno Almeida', 'CNH222333444', '1987-09-23', '04094-050', '200'),
    ('291.847.305-79', 'Carla Souza', 'CNH333444555', '1995-01-30', '13010-001', '300'),
    ('846.205.319-60', 'Diego Lima', 'CNH444555666', '1990-06-18', '01305-000', '415'),
    ('105.938.472-88', 'Eduarda Pereira', 'CNH555666777', '1984-11-02', '01310-200', '1578'),
    ('672.841.930-69', 'Fernanda Rocha', 'CNH666777888', '1998-03-21', '01504-001', '920'),
    ('458.120.639-15', 'Gabriel Santos', 'CNH777888999', '1991-12-08', '13073-001', '840'),
    ('309.761.584-93', 'Helena Costa', 'CNH888999000', '1989-07-14', '11010-151', '55'),
    ('917.253.640-34', 'Igor Barros', 'CNH999000111', '1993-10-05', '11060-002', '612'),
    ('640.382.917-31', 'Juliana Alves', 'CNH123456789', '1986-02-27', '13400-520', '88'),
    ('253.709.481-60', 'Karen Dias', 'CNH234567890', '1997-08-09', '13208-056', '1200'),
    ('798.164.205-11', 'Lucas Ribeiro', 'CNH345678901', '1994-05-16', '14015-110', '75'),
    ('120.495.683-98', 'Mariana Nunes', 'CNH456789012', '1988-01-11', '13419-155', '909'),
    ('934.681.720-87', 'Patricia Gomes', 'CNH567890123', '1996-09-19', '01010-001', '240'),
    ('516.270.934-43', 'Roberto Azevedo', 'CNH678901234', '1983-04-25', '04538-132', '3500')
) AS origem (cpf, nome, numero_habilitacao, data_nascimento, cep, numero)
ON destino.cpf = origem.cpf
WHEN NOT MATCHED THEN
    INSERT (cpf, nome, numero_habilitacao, data_nascimento, endereco_id)
    VALUES (
        origem.cpf,
        origem.nome,
        origem.numero_habilitacao,
        origem.data_nascimento,
        (SELECT TOP 1 id FROM dbo.endereco WHERE cep = origem.cep AND numero = origem.numero ORDER BY id)
    );

MERGE dbo.veiculo AS destino
USING (VALUES
    ('ABC1D23', 'Fiat', 'Argo', 'Prata', 2023, 'GASOLINA', 18500, 'MANUAL', 48.00, 'ALUGADO', 'Economico'),
    ('DEF4G56', 'Hyundai', 'HB20', 'Branco', 2022, 'ALCOOL', 30200, 'AUTOMATICO', 50.00, 'DISPONIVEL', 'Economico'),
    ('GHI7J89', 'Jeep', 'Compass', 'Cinza', 2024, 'GASOLINA', 9500, 'AUTOMATICO', 60.00, 'EM_REPARO', 'SUV'),
    ('JKL8M90', 'Toyota', 'Corolla', 'Preto', 2023, 'GASOLINA', 14100, 'AUTOMATICO', 55.00, 'DISPONIVEL', 'Luxo'),
    ('MNO2P34', 'Volkswagen', 'T-Cross', 'Azul', 2023, 'ALCOOL', 22000, 'AUTOMATICO', 52.00, 'DISPONIVEL', 'SUV'),
    ('PQR3S45', 'Chevrolet', 'Onix', 'Vermelho', 2024, 'ALCOOL', 8200, 'MANUAL', 44.00, 'ALUGADO', 'Economico'),
    ('QWE5R67', 'Renault', 'Kwid', 'Branco', 2023, 'GASOLINA', 15400, 'MANUAL', 38.00, 'EM_REPARO', 'Economico'),
    ('RTY6U78', 'Peugeot', '208', 'Cinza', 2024, 'ALCOOL', 7300, 'AUTOMATICO', 47.00, 'ALUGADO', 'Economico'),
    ('UIO7P89', 'Citroen', 'C3', 'Azul', 2022, 'GASOLINA', 26800, 'MANUAL', 47.00, 'DISPONIVEL', 'Economico'),
    ('ASD8F90', 'Honda', 'City', 'Prata', 2023, 'GASOLINA', 12600, 'AUTOMATICO', 50.00, 'ALUGADO', 'Luxo'),
    ('FGH1J23', 'Nissan', 'Kicks', 'Preto', 2022, 'ALCOOL', 34500, 'AUTOMATICO', 52.00, 'EM_REPARO', 'SUV'),
    ('ZXC4V56', 'Volkswagen', 'Nivus', 'Cinza', 2024, 'GASOLINA', 6600, 'AUTOMATICO', 52.00, 'ALUGADO', 'SUV'),
    ('BNM7K89', 'Fiat', 'Pulse', 'Branco', 2023, 'ALCOOL', 18750, 'AUTOMATICO', 47.00, 'DISPONIVEL', 'SUV'),
    ('HJK2L34', 'Toyota', 'Hilux', 'Prata', 2022, 'GASOLINA', 41200, 'AUTOMATICO', 80.00, 'DISPONIVEL', 'Picape'),
    ('TYU9I01', 'Chevrolet', 'S10', 'Preto', 2023, 'GASOLINA', 27800, 'AUTOMATICO', 76.00, 'ALUGADO', 'Picape'),
    ('WER8T76', 'Ford', 'Ranger', 'Azul', 2021, 'GASOLINA', 53200, 'AUTOMATICO', 80.00, 'EM_REPARO', 'Picape'),
    ('YUI5O43', 'Fiat', 'Toro', 'Vermelho', 2024, 'ALCOOL', 9700, 'AUTOMATICO', 60.00, 'DISPONIVEL', 'Picape'),
    ('PAS6D54', 'Mitsubishi', 'L200', 'Cinza', 2022, 'GASOLINA', 38600, 'MANUAL', 75.00, 'DISPONIVEL', 'Picape'),
    ('KLM3N21', 'Chevrolet', 'Spin', 'Branco', 2023, 'ALCOOL', 24100, 'AUTOMATICO', 53.00, 'EM_REPARO', 'Minivan'),
    ('QAZ2W34', 'Citroen', 'C4 Picasso', 'Prata', 2021, 'GASOLINA', 45400, 'AUTOMATICO', 57.00, 'ALUGADO', 'Minivan'),
    ('WSX3E45', 'Kia', 'Carnival', 'Preto', 2022, 'GASOLINA', 32900, 'AUTOMATICO', 72.00, 'DISPONIVEL', 'Minivan'),
    ('EDC4R56', 'Mercedes-Benz', 'Classe C', 'Preto', 2024, 'GASOLINA', 5400, 'AUTOMATICO', 66.00, 'ALUGADO', 'Luxo'),
    ('RFV5T67', 'BMW', 'X1', 'Branco', 2023, 'GASOLINA', 14900, 'AUTOMATICO', 61.00, 'EM_REPARO', 'Luxo'),
    ('TGB6Y78', 'Audi', 'A3', 'Cinza', 2024, 'GASOLINA', 4900, 'AUTOMATICO', 50.00, 'ALUGADO', 'Luxo'),
    ('YHN7U89', 'Volvo', 'XC40', 'Azul', 2023, 'GASOLINA', 16300, 'AUTOMATICO', 54.00, 'DISPONIVEL', 'Luxo'),
    ('UJM8I90', 'Fiat', 'Mobi', 'Branco', 2022, 'ALCOOL', 35800, 'MANUAL', 47.00, 'ALUGADO', 'Economico'),
    ('IKL9O01', 'Renault', 'Logan', 'Prata', 2021, 'GASOLINA', 48600, 'MANUAL', 50.00, 'DISPONIVEL', 'Economico'),
    ('OLP1P12', 'Volkswagen', 'Gol', 'Vermelho', 2020, 'ALCOOL', 62000, 'MANUAL', 55.00, 'INATIVO', 'Economico'),
    ('VBN2M23', 'Honda', 'HR-V', 'Preto', 2022, 'GASOLINA', 29500, 'AUTOMATICO', 51.00, 'DISPONIVEL', 'SUV'),
    ('XCV3B34', 'Jeep', 'Renegade', 'Verde', 2023, 'ALCOOL', 21800, 'AUTOMATICO', 60.00, 'DISPONIVEL', 'SUV')
) AS origem (placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem, tipo_cambio, capacidade_tanque, status, categoria_nome)
ON destino.placa = origem.placa
WHEN NOT MATCHED THEN
    INSERT (
        placa, marca, modelo, cor, ano, tipo_combustivel, quilometragem,
        tipo_cambio, capacidade_tanque, status, categoria_id
    )
    VALUES (
        origem.placa, origem.marca, origem.modelo, origem.cor, origem.ano,
        origem.tipo_combustivel, origem.quilometragem, origem.tipo_cambio,
        origem.capacidade_tanque, origem.status,
        (SELECT id FROM dbo.categoria WHERE nome = origem.categoria_nome)
    );

MERGE dbo.locacao AS destino
USING (VALUES
    ('ABC1D23', '582.349.761-09', CAST('2026-05-28' AS DATE), 4, 'ATIVA'),
    ('PQR3S45', '846.205.319-60', CAST('2026-05-30' AS DATE), 5, 'ATIVA'),
    ('RTY6U78', '672.841.930-69', CAST('2026-05-31' AS DATE), 2, 'ATIVA'),
    ('ASD8F90', '309.761.584-93', CAST('2026-05-29' AS DATE), 7, 'ATIVA'),
    ('ZXC4V56', '640.382.917-31', CAST('2026-05-27' AS DATE), 6, 'ATIVA'),
    ('TYU9I01', '798.164.205-11', CAST('2026-05-31' AS DATE), 3, 'ATIVA'),
    ('QAZ2W34', '934.681.720-87', CAST('2026-05-26' AS DATE), 8, 'ATIVA'),
    ('EDC4R56', '516.270.934-43', CAST('2026-05-31' AS DATE), 4, 'ATIVA'),
    ('TGB6Y78', '734.920.185-05', CAST('2026-05-30' AS DATE), 3, 'ATIVA'),
    ('UJM8I90', '291.847.305-79', CAST('2026-05-31' AS DATE), 2, 'ATIVA'),
    ('DEF4G56', '734.920.185-05', CAST('2026-05-20' AS DATE), 3, 'FINALIZADA'),
    ('JKL8M90', '291.847.305-79', CAST('2026-05-12' AS DATE), 5, 'FINALIZADA'),
    ('MNO2P34', '105.938.472-88', CAST('2026-05-10' AS DATE), 4, 'FINALIZADA'),
    ('UIO7P89', '458.120.639-15', CAST('2026-05-05' AS DATE), 2, 'FINALIZADA'),
    ('BNM7K89', '917.253.640-34', CAST('2026-04-28' AS DATE), 6, 'FINALIZADA'),
    ('HJK2L34', '120.495.683-98', CAST('2026-04-18' AS DATE), 7, 'FINALIZADA'),
    ('YUI5O43', '582.349.761-09', CAST('2026-04-12' AS DATE), 3, 'FINALIZADA'),
    ('PAS6D54', '253.709.481-60', CAST('2026-04-06' AS DATE), 5, 'FINALIZADA'),
    ('WSX3E45', '934.681.720-87', CAST('2026-03-22' AS DATE), 8, 'FINALIZADA'),
    ('YHN7U89', '516.270.934-43', CAST('2026-03-14' AS DATE), 4, 'FINALIZADA'),
    ('IKL9O01', '798.164.205-11', CAST('2026-03-08' AS DATE), 3, 'FINALIZADA'),
    ('VBN2M23', '846.205.319-60', CAST('2026-02-19' AS DATE), 6, 'FINALIZADA'),
    ('XCV3B34', '672.841.930-69', CAST('2026-02-01' AS DATE), 4, 'FINALIZADA')
) AS origem (veiculo_placa, locatario_cpf, data_retirada, quantidade_dias, status)
ON destino.veiculo_placa = origem.veiculo_placa
   AND destino.locatario_cpf = origem.locatario_cpf
   AND destino.data_retirada = origem.data_retirada
WHEN NOT MATCHED THEN
    INSERT (veiculo_placa, locatario_cpf, data_retirada, quantidade_dias, status)
    VALUES (origem.veiculo_placa, origem.locatario_cpf, origem.data_retirada, origem.quantidade_dias, origem.status);

MERGE dbo.devolucao AS destino
USING (
    SELECT locacao.id AS locacao_id, dados.data_devolucao, dados.litros_faltantes, dados.valor_combustivel, dados.valor_locacao, dados.valor_total
    FROM (VALUES
        ('DEF4G56', '734.920.185-05', CAST('2026-05-20' AS DATE), CAST('2026-05-23' AS DATE), 6.00, 33.00, 360.00, 393.00),
        ('JKL8M90', '291.847.305-79', CAST('2026-05-12' AS DATE), CAST('2026-05-17' AS DATE), 0.00, 0.00, 1900.00, 1900.00),
        ('MNO2P34', '105.938.472-88', CAST('2026-05-10' AS DATE), CAST('2026-05-14' AS DATE), 8.50, 46.75, 880.00, 926.75),
        ('UIO7P89', '458.120.639-15', CAST('2026-05-05' AS DATE), CAST('2026-05-07' AS DATE), 3.00, 16.50, 240.00, 256.50),
        ('BNM7K89', '917.253.640-34', CAST('2026-04-28' AS DATE), CAST('2026-05-04' AS DATE), 11.00, 60.50, 1320.00, 1380.50),
        ('HJK2L34', '120.495.683-98', CAST('2026-04-18' AS DATE), CAST('2026-04-25' AS DATE), 4.00, 22.00, 2100.00, 2122.00),
        ('YUI5O43', '582.349.761-09', CAST('2026-04-12' AS DATE), CAST('2026-04-15' AS DATE), 0.00, 0.00, 900.00, 900.00),
        ('PAS6D54', '253.709.481-60', CAST('2026-04-06' AS DATE), CAST('2026-04-11' AS DATE), 12.00, 66.00, 1500.00, 1566.00),
        ('WSX3E45', '934.681.720-87', CAST('2026-03-22' AS DATE), CAST('2026-03-30' AS DATE), 7.50, 41.25, 2080.00, 2121.25),
        ('YHN7U89', '516.270.934-43', CAST('2026-03-14' AS DATE), CAST('2026-03-18' AS DATE), 2.00, 11.00, 1520.00, 1531.00),
        ('IKL9O01', '798.164.205-11', CAST('2026-03-08' AS DATE), CAST('2026-03-11' AS DATE), 5.00, 27.50, 360.00, 387.50),
        ('VBN2M23', '846.205.319-60', CAST('2026-02-19' AS DATE), CAST('2026-02-25' AS DATE), 9.00, 49.50, 1320.00, 1369.50),
        ('XCV3B34', '672.841.930-69', CAST('2026-02-01' AS DATE), CAST('2026-02-05' AS DATE), 0.00, 0.00, 880.00, 880.00)
    ) AS dados (veiculo_placa, locatario_cpf, data_retirada, data_devolucao, litros_faltantes, valor_combustivel, valor_locacao, valor_total)
    INNER JOIN dbo.locacao locacao
        ON locacao.veiculo_placa = dados.veiculo_placa
       AND locacao.locatario_cpf = dados.locatario_cpf
       AND locacao.data_retirada = dados.data_retirada
) AS origem
ON destino.locacao_id = origem.locacao_id
WHEN NOT MATCHED THEN
    INSERT (locacao_id, data_devolucao, litros_faltantes, valor_combustivel, valor_locacao, valor_total)
    VALUES (origem.locacao_id, origem.data_devolucao, origem.litros_faltantes, origem.valor_combustivel, origem.valor_locacao, origem.valor_total);

MERGE dbo.reparo AS destino
USING (VALUES
    ('GHI7J89', CAST('2026-05-29' AS DATE), 5, 'Troca de pastilhas de freio e revisao preventiva', 850.00, 'EM_ANDAMENTO'),
    ('QWE5R67', CAST('2026-05-31' AS DATE), 2, 'Diagnostico de falha na injecao eletronica', 430.00, 'EM_ANDAMENTO'),
    ('FGH1J23', CAST('2026-05-30' AS DATE), 4, 'Substituicao dos amortecedores dianteiros', 1420.00, 'EM_ANDAMENTO'),
    ('WER8T76', CAST('2026-05-27' AS DATE), 7, 'Reparo no sistema de arrefecimento', 2100.00, 'EM_ANDAMENTO'),
    ('KLM3N21', CAST('2026-05-31' AS DATE), 3, 'Revisao eletrica e troca de bateria', 690.00, 'EM_ANDAMENTO'),
    ('RFV5T67', CAST('2026-05-28' AS DATE), 6, 'Correcao de ruido na suspensao traseira', 1680.00, 'EM_ANDAMENTO'),
    ('OLP1P12', CAST('2026-04-20' AS DATE), 10, 'Reparo estrutural e revisao completa antes da inativacao', 3200.00, 'FINALIZADO'),
    ('DEF4G56', CAST('2026-04-10' AS DATE), 2, 'Troca de pneus dianteiros', 760.00, 'FINALIZADO'),
    ('JKL8M90', CAST('2026-03-18' AS DATE), 3, 'Revisao dos freios ABS', 1150.00, 'FINALIZADO'),
    ('MNO2P34', CAST('2026-02-11' AS DATE), 4, 'Funilaria leve na porta traseira', 980.00, 'FINALIZADO')
) AS origem (veiculo_placa, data_entrada, quantidade_dias, descricao_problema, valor_reparo, status)
ON destino.veiculo_placa = origem.veiculo_placa
   AND destino.data_entrada = origem.data_entrada
   AND destino.descricao_problema = origem.descricao_problema
WHEN NOT MATCHED THEN
    INSERT (veiculo_placa, data_entrada, quantidade_dias, descricao_problema, valor_reparo, status)
    VALUES (origem.veiculo_placa, origem.data_entrada, origem.quantidade_dias, origem.descricao_problema, origem.valor_reparo, origem.status);

UPDATE dbo.veiculo
SET status = 'ALUGADO'
WHERE placa IN ('ABC1D23', 'PQR3S45', 'RTY6U78', 'ASD8F90', 'ZXC4V56', 'TYU9I01', 'QAZ2W34', 'EDC4R56', 'TGB6Y78', 'UJM8I90');

UPDATE dbo.veiculo
SET status = 'EM_REPARO'
WHERE placa IN ('GHI7J89', 'QWE5R67', 'FGH1J23', 'WER8T76', 'KLM3N21', 'RFV5T67');

UPDATE dbo.veiculo
SET status = 'INATIVO'
WHERE placa IN ('OLP1P12');

UPDATE dbo.veiculo
SET status = 'DISPONIVEL'
WHERE placa IN (
    'DEF4G56', 'JKL8M90', 'MNO2P34', 'UIO7P89', 'BNM7K89',
    'HJK2L34', 'YUI5O43', 'PAS6D54', 'WSX3E45', 'YHN7U89',
    'IKL9O01', 'VBN2M23', 'XCV3B34'
);

COMMIT TRANSACTION;
GO
