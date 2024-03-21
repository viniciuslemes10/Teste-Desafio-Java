create table clientes(
    id bigint auto_increment primary key,
    nome varchar(150) not null,
    cpf varchar(14) not null unique,
    email varchar(255) not null unique,
    saldo decimal(18, 2)
);