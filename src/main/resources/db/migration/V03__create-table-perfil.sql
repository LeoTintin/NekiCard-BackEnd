CREATE TABLE perfil (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    data_nascimento DATE,
    foto VARCHAR(255),
    telefone VARCHAR(20),
    rede_social VARCHAR(255)
);
