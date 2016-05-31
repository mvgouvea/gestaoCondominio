CREATE DATABASE IF NOT EXISTS CONDOMINIO;
USE CONDOMINIO;

-- INSERT TIPO_USUARIO
INSERT INTO PERFIL(DESCRICAO) VALUES("Administrador");
INSERT INTO PERFIL(DESCRICAO) VALUES("Condômino");
INSERT INTO PERFIL(DESCRICAO) VALUES("Fornecedor");
INSERT INTO PERFIL(DESCRICAO) VALUES("Funcionário");
INSERT INTO PERFIL(DESCRICAO) VALUES("Locatário");

-- INSERT GRUPO
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Gerenciar Grupos", "GERENCIAR_GRUPOS");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Administrador", "MANTER_ADMINISTRADOR");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Condômino", "MANTER_CONDOMINO");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Fornecedor", "MANTER_FORNECEDOR");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Funcionário", "MANTER_FUNCIONARIO");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Locatário", "MANTER_LOCATARIO");
INSERT INTO GRUPO(DESCRICAO, ROLE) VALUES("Manter Serviços", "MANTER_SERVICOS");

-- INSERT GRUPO_PERFIL
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 1, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 2, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 0, 3, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 0, 4, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 0, 5, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 0, 6, 1);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 0, 7, 1);

INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 1, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 2, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 3, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 4, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 5, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 6, 2);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 7, 2);

INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 1, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 2, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 3, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 4, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 5, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 6, 3);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 7, 3);

INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 1, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 2, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 3, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 4, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 5, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 6, 4);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 7, 4);

INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 1, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 2, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 3, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 4, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 5, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(1, 1, 6, 5);
INSERT INTO GRUPO_PERFIL(PERMITIDO, DESABILITADO, ID_GRUPO, ID_PERFIL) VALUES(0, 1, 7, 5);