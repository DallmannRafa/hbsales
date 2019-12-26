CREATE TABLE seg_funcionarios
(
    id BIGINT IDENTITY (1,1) NOT NULL,
    nome VARCHAR (50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    uuid VARCHAR(36) NOT NULL,
);

create unique index ix_seg_funcionarios_01 on seg_funcionarios (uuid asc);