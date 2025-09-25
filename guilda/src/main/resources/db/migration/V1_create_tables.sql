CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          nome VARCHAR(100) NOT NULL,
                          avaliacao INT,
                          moedas INT,
                          missao_ativa_id BIGINT,
                          missao_criada_id BIGINT
);

CREATE TABLE missoes (
                         id SERIAL PRIMARY KEY,
                         descricao VARCHAR(255) NOT NULL,
                         prazo VARCHAR(50),
                         recompensa INT
);

ALTER TABLE usuarios
    ADD CONSTRAINT fk_missao_ativa FOREIGN KEY (missao_ativa_id) REFERENCES missoes(id),
    ADD CONSTRAINT fk_missao_criada FOREIGN KEY (missao_criada_id) REFERENCES missoes(id);
