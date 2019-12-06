create table seg_categorias
(
    id    BIGINT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    codigo_categoria VARCHAR(10) UNIQUE NOT NULL,
    nome_categoria  VARCHAR(50) NOT NULL,
    id_fornecedor BIGINT NOT NULL
    FOREIGN KEY REFERENCES seg_fornecedores (id)
)