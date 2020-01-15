CREATE TABLE seg_pedidos
(
    id BIGINT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    codigo VARCHAR (10) NOT NULL,
    status VARCHAR (10) NOT NULL,
    id_funcionario BIGINT NOT NULL FOREIGN KEY REFERENCES seg_funcionarios (id),
    id_fornecedor BIGINT NOT NULL FOREIGN KEY REFERENCES seg_fornecedores (id),
    id_periodo BIGINT NOT NULL FOREIGN KEY REFERENCES seg_periodo_vendas (id),
    data DATE NOT NULL
);