create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    razao_social VARCHAR(100)    NOT NULL,
    cnpj VARCHAR (14)            UNIQUE NOT NULL,
    nome_fantasia VARCHAR(100)   NOT NULL,
    endereco VARCHAR (100)       NOT NULL,
    telefone VARCHAR (12)        NOT NULL,
    email VARCHAR (100)          NOT NULL,
    PRIMARY KEY (id)
);