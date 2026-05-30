IF DB_ID(N'locadora') IS NULL
BEGIN
    CREATE DATABASE locadora;
END;
GO

USE locadora;
GO

IF OBJECT_ID(N'dbo.categoria', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.categoria (
        id BIGINT IDENTITY(1,1) NOT NULL,
        nome VARCHAR(80) NOT NULL,
        descricao VARCHAR(255) NULL,
        valor_diaria DECIMAL(10,2) NOT NULL,

        CONSTRAINT pk_categoria PRIMARY KEY (id),
        CONSTRAINT uq_categoria_nome UNIQUE (nome),
        CONSTRAINT chk_categoria_nome CHECK (LEN(LTRIM(RTRIM(nome))) > 0),
        CONSTRAINT chk_categoria_valor_diaria CHECK (valor_diaria > 0)
    );
END;
GO

IF OBJECT_ID(N'dbo.endereco', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.endereco (
        id BIGINT IDENTITY(1,1) NOT NULL,
        logradouro VARCHAR(120) NOT NULL,
        numero VARCHAR(20) NOT NULL,
        cep VARCHAR(10) NOT NULL,
        cidade VARCHAR(80) NOT NULL,

        CONSTRAINT pk_endereco PRIMARY KEY (id),
        CONSTRAINT chk_endereco_logradouro CHECK (LEN(LTRIM(RTRIM(logradouro))) > 0),
        CONSTRAINT chk_endereco_numero CHECK (LEN(LTRIM(RTRIM(numero))) > 0),
        CONSTRAINT chk_endereco_cep CHECK (
            cep LIKE '[0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9]'
            OR cep LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
        ),
        CONSTRAINT chk_endereco_cidade CHECK (LEN(LTRIM(RTRIM(cidade))) > 0)
    );
END;
GO

IF OBJECT_ID(N'dbo.veiculo', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.veiculo (
        placa VARCHAR(10) NOT NULL,
        marca VARCHAR(80) NOT NULL,
        modelo VARCHAR(80) NOT NULL,
        cor VARCHAR(40) NOT NULL,
        ano INT NOT NULL,
        tipo_combustivel VARCHAR(20) NOT NULL,
        quilometragem INT NOT NULL,
        tipo_cambio VARCHAR(20) NOT NULL,
        capacidade_tanque DECIMAL(6,2) NOT NULL,
        status VARCHAR(20) NOT NULL,
        categoria_id BIGINT NOT NULL,

        CONSTRAINT pk_veiculo PRIMARY KEY (placa),
        CONSTRAINT fk_veiculo_categoria
            FOREIGN KEY (categoria_id) REFERENCES dbo.categoria(id),
        CONSTRAINT chk_veiculo_placa CHECK (
            placa LIKE '[A-Z][A-Z][A-Z][0-9][A-Z][0-9][0-9]'
            OR placa LIKE '[A-Z][A-Z][A-Z]-[0-9][0-9][0-9][0-9]'
            OR placa LIKE '[A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9]'
        ),
        CONSTRAINT chk_veiculo_marca CHECK (LEN(LTRIM(RTRIM(marca))) > 0),
        CONSTRAINT chk_veiculo_modelo CHECK (LEN(LTRIM(RTRIM(modelo))) > 0),
        CONSTRAINT chk_veiculo_cor CHECK (LEN(LTRIM(RTRIM(cor))) > 0),
        CONSTRAINT chk_veiculo_ano CHECK (ano >= 1980),
        CONSTRAINT chk_veiculo_tipo_combustivel CHECK (tipo_combustivel IN ('GASOLINA', 'ALCOOL')),
        CONSTRAINT chk_veiculo_quilometragem CHECK (quilometragem >= 0),
        CONSTRAINT chk_veiculo_tipo_cambio CHECK (tipo_cambio IN ('MANUAL', 'AUTOMATICO')),
        CONSTRAINT chk_veiculo_capacidade_tanque CHECK (capacidade_tanque > 0),
        CONSTRAINT chk_veiculo_status CHECK (status IN ('DISPONIVEL', 'ALUGADO', 'EM_REPARO', 'INATIVO'))
    );
END;
GO

IF OBJECT_ID(N'dbo.locatario', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.locatario (
        cpf VARCHAR(14) NOT NULL,
        nome VARCHAR(120) NOT NULL,
        numero_habilitacao VARCHAR(30) NOT NULL,
        data_nascimento DATE NOT NULL,
        endereco_id BIGINT NOT NULL,

        CONSTRAINT pk_locatario PRIMARY KEY (cpf),
        CONSTRAINT uq_locatario_numero_habilitacao UNIQUE (numero_habilitacao),
        CONSTRAINT fk_locatario_endereco
            FOREIGN KEY (endereco_id) REFERENCES dbo.endereco(id),
        CONSTRAINT chk_locatario_cpf CHECK (
            cpf LIKE '[0-9][0-9][0-9].[0-9][0-9][0-9].[0-9][0-9][0-9]-[0-9][0-9]'
            OR cpf LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
        ),
        CONSTRAINT chk_locatario_nome CHECK (LEN(LTRIM(RTRIM(nome))) > 0),
        CONSTRAINT chk_locatario_numero_habilitacao CHECK (LEN(LTRIM(RTRIM(numero_habilitacao))) > 0)
    );
END;
GO

IF OBJECT_ID(N'dbo.locacao', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.locacao (
        id BIGINT IDENTITY(1,1) NOT NULL,
        veiculo_placa VARCHAR(10) NOT NULL,
        locatario_cpf VARCHAR(14) NOT NULL,
        data_retirada DATE NOT NULL,
        quantidade_dias INT NOT NULL,
        status VARCHAR(20) NOT NULL,

        CONSTRAINT pk_locacao PRIMARY KEY (id),
        CONSTRAINT fk_locacao_veiculo
            FOREIGN KEY (veiculo_placa) REFERENCES dbo.veiculo(placa),
        CONSTRAINT fk_locacao_locatario
            FOREIGN KEY (locatario_cpf) REFERENCES dbo.locatario(cpf),
        CONSTRAINT chk_locacao_quantidade_dias CHECK (quantidade_dias > 0),
        CONSTRAINT chk_locacao_status CHECK (status IN ('ATIVA', 'FINALIZADA'))
    );
END;
GO

IF OBJECT_ID(N'dbo.devolucao', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.devolucao (
        id BIGINT IDENTITY(1,1) NOT NULL,
        locacao_id BIGINT NOT NULL,
        data_devolucao DATE NOT NULL,
        litros_faltantes DECIMAL(6,2) NOT NULL,
        valor_combustivel DECIMAL(10,2) NOT NULL,
        valor_locacao DECIMAL(10,2) NOT NULL,
        valor_total DECIMAL(10,2) NOT NULL,

        CONSTRAINT pk_devolucao PRIMARY KEY (id),
        CONSTRAINT uq_devolucao_locacao UNIQUE (locacao_id),
        CONSTRAINT fk_devolucao_locacao
            FOREIGN KEY (locacao_id) REFERENCES dbo.locacao(id),
        CONSTRAINT chk_devolucao_litros_faltantes CHECK (litros_faltantes >= 0),
        CONSTRAINT chk_devolucao_valor_combustivel CHECK (valor_combustivel >= 0),
        CONSTRAINT chk_devolucao_valor_locacao CHECK (valor_locacao >= 0),
        CONSTRAINT chk_devolucao_valor_total CHECK (valor_total >= 0)
    );
END;
GO

IF OBJECT_ID(N'dbo.reparo', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.reparo (
        id BIGINT IDENTITY(1,1) NOT NULL,
        veiculo_placa VARCHAR(10) NOT NULL,
        data_entrada DATE NOT NULL,
        quantidade_dias INT NOT NULL,
        descricao_problema VARCHAR(MAX) NOT NULL,
        valor_reparo DECIMAL(10,2) NOT NULL,
        status VARCHAR(20) NOT NULL,

        CONSTRAINT pk_reparo PRIMARY KEY (id),
        CONSTRAINT fk_reparo_veiculo
            FOREIGN KEY (veiculo_placa) REFERENCES dbo.veiculo(placa),
        CONSTRAINT chk_reparo_quantidade_dias CHECK (quantidade_dias > 0),
        CONSTRAINT chk_reparo_descricao_problema CHECK (LEN(LTRIM(RTRIM(descricao_problema))) > 0),
        CONSTRAINT chk_reparo_valor_reparo CHECK (valor_reparo >= 0),
        CONSTRAINT chk_reparo_status CHECK (status IN ('EM_ANDAMENTO', 'FINALIZADO'))
    );
END;
GO
