CREATE TABLE seg_produtos
(
    id    BIGINT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
    codigo_produto VARCHAR (10) UNIQUE NOT NULL,
    nome_produto VARCHAR (200) NOT NULL,
    preco_produto DECIMAL(10,2) NOT NULL,
    id_linha_categoria BIGINT NOT NULL FOREIGN KEY REFERENCES seg_linha_categorias (id),
    unidade_por_caixa INT NOT NULL,
    peso_unidade DECIMAL (10,3) NOT NULL,
    unidade_medida_peso VARCHAR (2) NOT NULL,
    validade DATE NOT NULL,
)