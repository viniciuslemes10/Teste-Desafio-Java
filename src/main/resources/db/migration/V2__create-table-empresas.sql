create table empresas(
    id bigint auto_increment primary key,
    nome varchar(150) not null,
    cnpj varchar(18) not null unique,
    email varchar(255) not null unique,
    saldo decimal(18, 2)
);