CREATE TABLE gatinhos (
                         id SERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         raca VARCHAR(100) UNIQUE NOT NULL,
                         telefone_dono VARCHAR(15)
);