create table seg_categorias
(
    id    BIGINT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    codigo_categoria VARCHAR(12) NOT NULL,
    nome_categoria  VARCHAR(100) NOT NULL,
    id_fornecedor BIGINT
    FOREIGN KEY REFERENCES seg_fornecedores (id)
)