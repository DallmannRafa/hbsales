CREATE TABLE seg_itens
(
    id BIGINT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    quantidade INT NOT NULL,
    id_pedido BIGINT NOT NULL FOREIGN KEY REFERENCES seg_pedidos (id),
    id_produto BIGINT NOT NULL FOREIGN KEY REFERENCES seg_produtos (id)
);