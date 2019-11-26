create table seg_categorias
(
    id    BIGINT IDENTITY (1, 1) NOT NULL,
    codigo_categoria VARCHAR(12) NOT NULL,
    fornecedor_categoria VARCHAR(100) NOT NULL,
    nome_categoria VARCHAR(100) NOT NULL,
    id_fornecedor INT (10) NOT NULL,
    KEY fornecedor_categoria_fk (id_fornecedor),
    CONSTRAINT fornecedor_categoria_fk FOREIGN KEY (id_fornecedor) REFERENCES seg_fornecedores (id)
)