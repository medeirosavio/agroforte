-- Tabela Pessoa (superclasse)
CREATE TABLE pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150),
    celular VARCHAR(20),
    nacionalidade VARCHAR(100)
);

-- Tabela PessoaFisica
CREATE TABLE pessoa_fisica (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    rg VARCHAR(20),
    estado_civil VARCHAR(50),
    genero VARCHAR(20),
    data_nascimento DATE,
    nome_mae VARCHAR(150),
    pessoa_id INT UNIQUE NOT NULL REFERENCES pessoa(id)
);

-- Tabela PessoaJuridica
CREATE TABLE pessoa_juridica (
    id SERIAL PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    razao_social VARCHAR(150) NOT NULL,
    inscricao_estadual VARCHAR(50),
    inscricao_municipal VARCHAR(50),
    pessoa_id INT UNIQUE NOT NULL REFERENCES pessoa(id)
);

-- Tabela Operacao
CREATE TABLE operacao (
    id SERIAL PRIMARY KEY,
    data_inicio DATE NOT NULL,
    data_emissao DATE NOT NULL,
    data_fim DATE,
    quantidade_parcelas INT NOT NULL,
    data_primeira_parcela DATE NOT NULL,
    tempo_carencia INT,
    valor_operacao NUMERIC(15,2) NOT NULL,
    taxa_mensal NUMERIC(5,2) NOT NULL,
    pessoa_id INT NOT NULL REFERENCES pessoa(id)
);

-- Tabela Parcela
CREATE TABLE parcela (
    id SERIAL PRIMARY KEY,
    numero_parcela INT NOT NULL,
    data_vencimento DATE NOT NULL,
    valor_parcela NUMERIC(15,2) NOT NULL,
    operacao_id INT NOT NULL REFERENCES operacao(id)
);
