create table seg_categorias
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    codigo_categoria VARCHAR(12) NOT NULL,
    fornecedor_categoria VARCHAR(100) NOT NULL,
    nome_categoria VARCHAR(100) NOT NULL,
)