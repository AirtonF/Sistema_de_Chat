

CREATE TABLE usuario (
	
      id BIGINT NOT NULL AUTO_INCREMENT,
      login VARCHAR(50) NOT NULL,
      senha VARCHAR(50) NOT NULL,
      nome VARCHAR(50) NOT NULL,
      email VARCHAR(50) NOT NULL,
      PRIMARY KEY(id),
      UNIQUE (senha)

);