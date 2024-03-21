CREATE TABLE transacao (
     id bigint AUTO_INCREMENT PRIMARY KEY,
     valor DECIMAL(18,2),
     data TIMESTAMP,
     cliente_id bigint,
     empresa_id bigint,
     FOREIGN KEY (cliente_id) REFERENCES clientes(id),
     FOREIGN KEY (empresa_id) REFERENCES empresas(id)
);