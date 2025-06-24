-- Popula a tabela parcela com as parcelas das operações (IDs 1 a 5)

-- Para simplificar, vamos inserir algumas parcelas para as primeiras parcelas de cada operação.

INSERT INTO parcela (
    id,
    numero_parcela,
    data_vencimento,
    valor_parcela,
    operacao_id
) VALUES
-- Operacao 1: 12 parcelas, valor parcela = 12000 / 12 = 1000
(1, 1, '2024-02-01', 1000.00, 1),
(2, 2, '2024-03-01', 1000.00, 1),
(3, 3, '2024-04-01', 1000.00, 1),

-- Operacao 2: 10 parcelas, valor parcela = 8000 / 10 = 800
(4, 1, '2024-04-10', 800.00, 2),
(5, 2, '2024-05-10', 800.00, 2),

-- Operacao 3: 8 parcelas, valor parcela = 15000 / 8 = 1875
(6, 1, '2024-06-05', 1875.00, 3),
(7, 2, '2024-07-05', 1875.00, 3),

-- Operacao 4: 6 parcelas, valor parcela = 5000 / 6 ≈ 833.33
(8, 1, '2024-08-20', 833.33, 4),
(9, 2, '2024-09-20', 833.33, 4),

-- Operacao 5: 24 parcelas, valor parcela = 25000 / 24 ≈ 1041.67
(10, 1, '2024-10-15', 1041.67, 5),
(11, 2, '2024-11-15', 1041.67, 5);