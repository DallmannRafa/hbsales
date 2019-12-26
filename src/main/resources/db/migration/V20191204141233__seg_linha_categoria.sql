create table seg_linha_categorias
(
    id              BIGINT IDENTITY (1,1) NOT NULL,
    nome_linha_categoria    VARCHAR (100) NOT NULL,
    codigo_linha_categoria   VARCHAR (24) NOT NULL,
    id_categoria_da_linha BIGINT
    FOREIGN KEY REFERENCES seg_categorias (id)
)