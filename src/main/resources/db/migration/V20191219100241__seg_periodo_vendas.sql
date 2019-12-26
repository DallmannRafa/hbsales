CREATE TABLE seg_periodo_vendas
(
    id BIGINT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    inicio_vendas DATE NOT NULL,
    fim_vendas DATE NOT NULL,
    id_fornecedor BIGINT NOT NULL FOREIGN KEY REFERENCES seg_fornecedores (id),
    retirada_pedido DATE NOT NULL,
    descricao VARCHAR (50) NOT NULL,

)