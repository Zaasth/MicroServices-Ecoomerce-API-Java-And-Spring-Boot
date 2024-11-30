CREATE TABLE cachorros (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    idade INT NOT NULL,
    raca VARCHAR(50) NOT NULL,
    data_adocao DATE
);