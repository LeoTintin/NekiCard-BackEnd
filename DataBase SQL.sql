CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE perfil (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    data_nascimento DATE NOT NULL,
    foto VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    rede_social VARCHAR(255)
);