CREATE DATABASE IF NOT EXISTS CONDOMINIO;
USE CONDOMINIO;

DROP TABLE IF EXISTS USUARIO;
DROP TABLE IF EXISTS PESSOA_SERVICO;
DROP TABLE IF EXISTS SERVICO;
DROP TABLE IF EXISTS PESSOA;
DROP TABLE IF EXISTS GRUPO_PERFIL;
DROP TABLE IF EXISTS PERFIL;
DROP TABLE IF EXISTS GRUPO;


-- CREATE TABLES

CREATE TABLE SERVICO (
	ID_SERVICO INT AUTO_INCREMENT,
	NOME VARCHAR(40) NOT NULL,
	DESCRICAO VARCHAR(70) NOT NULL,
	PRIMARY KEY (ID_SERVICO)
);

CREATE TABLE PERFIL (
	ID_PERFIL INT AUTO_INCREMENT,
	DESCRICAO VARCHAR(15) NOT NULL,
	PRIMARY KEY (ID_PERFIL)
);

CREATE TABLE GRUPO (
	ID_GRUPO INT AUTO_INCREMENT,
	DESCRICAO VARCHAR(50) NOT NULL,
	ROLE VARCHAR(50) NOT NULL,
	PRIMARY KEY (ID_GRUPO)
);

CREATE TABLE GRUPO_PERFIL (
	ID_GRUPO_PERFIL INT AUTO_INCREMENT,
	PERMITIDO BOOLEAN NOT NULL,
	DESABILITADO BOOLEAN NOT NULL,
	ID_GRUPO INT NOT NULL,
	ID_PERFIL INT NOT NULL,
	PRIMARY KEY (ID_GRUPO_PERFIL),
	FOREIGN KEY (ID_GRUPO) REFERENCES GRUPO (ID_GRUPO),
	FOREIGN KEY (ID_PERFIL) REFERENCES PERFIL (ID_PERFIL)
);

CREATE TABLE PESSOA (
	ID_PESSOA INT AUTO_INCREMENT,
	NOME VARCHAR(70) NOT NULL,
	SOBRENOME VARCHAR(70),
	RAZAO_SOCIAL VARCHAR(70),
	TELEFONE VARCHAR(11) NOT NULL,
	RG BIGINT(10),
	CPF VARCHAR(11),
	CNPJ VARCHAR(14),
	DATA_NASCIMENTO DATE,
	CEP VARCHAR(8) NOT NULL,
	ESTADO VARCHAR(50) NOT NULL,
	CIDADE VARCHAR(50) NOT NULL,
	BAIRRO VARCHAR(50) NOT NULL,
	NUMERO VARCHAR(4),
	ENDERECO VARCHAR(80) NOT NULL,
	COMPLEMENTO VARCHAR(80),
	ID_TIPO_GENERO VARCHAR(1) NOT NULL,
	ID_TIPO_ESTADO_CIVIL INT(1) NOT NULL,
	ID_TIPO_PESSOA INT(1) NOT NULL,
	PRIMARY KEY(ID_PESSOA)
);

CREATE TABLE PESSOA_SERVICO (
	ID_PESSOA_SERVICO INT AUTO_INCREMENT,
	ID_PESSOA INT NOT NULL,
	ID_SERVICO INT NOT NULL,
	PRIMARY KEY (ID_PESSOA_SERVICO),
	FOREIGN KEY (ID_PESSOA) REFERENCES PESSOA (ID_PESSOA),
	FOREIGN KEY (ID_SERVICO) REFERENCES SERVICO (ID_SERVICO)
);

CREATE TABLE USUARIO (
	ID_USUARIO INT AUTO_INCREMENT,
	EMAIL VARCHAR(100) NOT NULL,
	SENHA VARCHAR(32) NOT NULL,
	ATIVO BOOLEAN NOT NULL,
	PRIMEIRO_ACESSO BOOLEAN NOT NULL,
	ID_PESSOA INT NOT NULL,
	ID_PERFIL INT NOT NULL,
	PRIMARY KEY (ID_USUARIO),
	FOREIGN KEY (ID_PESSOA) REFERENCES PESSOA (ID_PESSOA),
	FOREIGN KEY (ID_PERFIL) REFERENCES PERFIL (ID_PERFIL)
);

