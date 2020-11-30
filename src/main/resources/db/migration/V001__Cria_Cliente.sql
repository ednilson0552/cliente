create table cliente (
	id bigint not null auto_increment,
    nome varchar(60) not null,
    sexo varchar(10),
    email varchar(255),
    dataNascimento date not null,
    naturalidade varchar(20),
    nacionalidade varchar(20),
    cpf varchar(14) not null,
    dataCadastro date,
    
    primary key (id)
);
