ALTER TABLE seg_linha_categorias ALTER COLUMN nome_linha_categoria VARCHAR (50) NOT NULL;
ALTER TABLE seg_linha_categorias ALTER COLUMN codigo_linha_categoria VARCHAR (10) NOT NULL;
ALTER TABLE seg_linha_categorias ADD UNIQUE (codigo_linha_categoria);
ALTER TABLE seg_linha_categorias ALTER COLUMN id_categoria_da_linha BIGINT NOT NULL;