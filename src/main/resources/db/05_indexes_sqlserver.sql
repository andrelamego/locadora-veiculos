USE locadora;
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_veiculo_categoria' AND object_id = OBJECT_ID(N'dbo.veiculo'))
    CREATE INDEX idx_veiculo_categoria ON dbo.veiculo(categoria_id);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_veiculo_status' AND object_id = OBJECT_ID(N'dbo.veiculo'))
    CREATE INDEX idx_veiculo_status ON dbo.veiculo(status);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_locacao_status' AND object_id = OBJECT_ID(N'dbo.locacao'))
    CREATE INDEX idx_locacao_status ON dbo.locacao(status);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_locacao_data_retirada' AND object_id = OBJECT_ID(N'dbo.locacao'))
    CREATE INDEX idx_locacao_data_retirada ON dbo.locacao(data_retirada);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_locacao_locatario' AND object_id = OBJECT_ID(N'dbo.locacao'))
    CREATE INDEX idx_locacao_locatario ON dbo.locacao(locatario_cpf);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_locacao_veiculo' AND object_id = OBJECT_ID(N'dbo.locacao'))
    CREATE INDEX idx_locacao_veiculo ON dbo.locacao(veiculo_placa);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_reparo_status' AND object_id = OBJECT_ID(N'dbo.reparo'))
    CREATE INDEX idx_reparo_status ON dbo.reparo(status);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_reparo_data_entrada' AND object_id = OBJECT_ID(N'dbo.reparo'))
    CREATE INDEX idx_reparo_data_entrada ON dbo.reparo(data_entrada);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'idx_reparo_veiculo' AND object_id = OBJECT_ID(N'dbo.reparo'))
    CREATE INDEX idx_reparo_veiculo ON dbo.reparo(veiculo_placa);
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ux_locacao_veiculo_ativa' AND object_id = OBJECT_ID(N'dbo.locacao'))
    CREATE UNIQUE INDEX ux_locacao_veiculo_ativa
        ON dbo.locacao(veiculo_placa)
        WHERE status = 'ATIVA';
GO

IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'ux_reparo_veiculo_em_andamento' AND object_id = OBJECT_ID(N'dbo.reparo'))
    CREATE UNIQUE INDEX ux_reparo_veiculo_em_andamento
        ON dbo.reparo(veiculo_placa)
        WHERE status = 'EM_ANDAMENTO';
GO
