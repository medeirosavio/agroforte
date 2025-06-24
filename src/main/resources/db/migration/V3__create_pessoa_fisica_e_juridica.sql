-- Tabela PessoaFisica
CREATE TABLE pessoa_fisica (
    id BIGINT PRIMARY KEY,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    rg VARCHAR(20),
    estado_civil VARCHAR(50),
    genero VARCHAR(20),
    data_nascimento DATE,
    nome_mae VARCHAR(150),
    CONSTRAINT fk_pessoa_fisica FOREIGN KEY (id) REFERENCES pessoa(id)
);

-- Tabela PessoaJuridica
CREATE TABLE pessoa_juridica (
    id BIGINT PRIMARY KEY,
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    razao_social VARCHAR(150) NOT NULL,
    inscricao_estadual VARCHAR(50),
    inscricao_municipal VARCHAR(50),
    CONSTRAINT fk_pessoa_juridica FOREIGN KEY (id) REFERENCES pessoa(id)
);