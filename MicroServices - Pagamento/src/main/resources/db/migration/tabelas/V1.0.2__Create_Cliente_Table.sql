CREATE TABLE cliente (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         telefone VARCHAR(15),
                         data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);