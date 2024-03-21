CREATE TABLE cliente_empresa (
    cliente_id bigint,
    empresa_id bigint,
    PRIMARY KEY (cliente_id, empresa_id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (empresa_id) REFERENCES empresas(id)
);