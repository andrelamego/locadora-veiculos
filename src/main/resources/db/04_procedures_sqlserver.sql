USE locadora;
GO

CREATE OR ALTER PROCEDURE dbo.sp_carros_disponiveis_cursor
    @categoria_id BIGINT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE
        @placa VARCHAR(10),
        @marca VARCHAR(80),
        @modelo VARCHAR(80),
        @cor VARCHAR(40),
        @ano INT,
        @tipo_combustivel VARCHAR(20),
        @quilometragem INT,
        @tipo_cambio VARCHAR(20),
        @capacidade_tanque DECIMAL(6,2),
        @status VARCHAR(20),
        @categoria_id_resultado BIGINT,
        @categoria VARCHAR(80),
        @valor_diaria DECIMAL(10,2);

    CREATE TABLE #carros_disponiveis (
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
        categoria VARCHAR(80) NOT NULL,
        valor_diaria DECIMAL(10,2) NOT NULL
    );

    DECLARE carros_cursor CURSOR LOCAL FAST_FORWARD FOR
        SELECT
            placa,
            marca,
            modelo,
            cor,
            ano,
            tipo_combustivel,
            quilometragem,
            tipo_cambio,
            capacidade_tanque,
            status,
            categoria_id,
            categoria,
            valor_diaria
        FROM dbo.fn_carros_disponiveis(@categoria_id);

    OPEN carros_cursor;

    FETCH NEXT FROM carros_cursor INTO
        @placa, @marca, @modelo, @cor, @ano,
        @tipo_combustivel, @quilometragem, @tipo_cambio,
        @capacidade_tanque, @status, @categoria_id_resultado,
        @categoria, @valor_diaria;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        INSERT INTO #carros_disponiveis (
            placa,
            marca,
            modelo,
            cor,
            ano,
            tipo_combustivel,
            quilometragem,
            tipo_cambio,
            capacidade_tanque,
            status,
            categoria_id,
            categoria,
            valor_diaria
        )
        VALUES (
            @placa,
            @marca,
            @modelo,
            @cor,
            @ano,
            @tipo_combustivel,
            @quilometragem,
            @tipo_cambio,
            @capacidade_tanque,
            @status,
            @categoria_id_resultado,
            @categoria,
            @valor_diaria
        );

        FETCH NEXT FROM carros_cursor INTO
            @placa, @marca, @modelo, @cor, @ano,
            @tipo_combustivel, @quilometragem, @tipo_cambio,
            @capacidade_tanque, @status, @categoria_id_resultado,
            @categoria, @valor_diaria;
    END;

    CLOSE carros_cursor;
    DEALLOCATE carros_cursor;

    SELECT
        placa,
        marca,
        modelo,
        cor,
        ano,
        tipo_combustivel,
        quilometragem,
        tipo_cambio,
        capacidade_tanque,
        status,
        categoria_id,
        categoria,
        valor_diaria
    FROM #carros_disponiveis
    ORDER BY marca, modelo;
END;
GO
