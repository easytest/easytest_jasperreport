create table projeto(
  i_projeto integer not null,
  nome varchar(30),
  descricao varchar(500),
  report varchar(1),
  ativo varchar(1),
  primary key(i_projeto)
)



;
create table banco_dados(
  i_projeto integer not null,
  driver varchar(100),
  url varchar(100),
  usuario varchar(30),
  senha varchar(30),
  primary key(i_projeto),
  FOREIGN KEY(i_projeto)
  REFERENCES projeto(i_projeto)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION  
)
;
create table caso_teste(
   i_projeto integer not null,
   i_caso_teste integer not null,
   nome varchar(50) not null,
   descricao varchar(500),
   relatorio_jasper varchar(30),
   mensagens varchar(500),
   compare_error varchar(1),
   script_test_data BLOB not null,
   script_execute_data BLOB,
   primary key(i_projeto, i_caso_teste),
  FOREIGN KEY(i_projeto)
  REFERENCES projeto(i_projeto)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION   
)
;
create table parametro(
   i_projeto integer not null,
   i_caso_teste integer not null,
   i_parametro integer not null,
   nome varchar(50) not null,
   valor varchar(50) not null,
   tipo varchar(1) not null,
   primary key(i_projeto, i_caso_teste,i_parametro),
  FOREIGN KEY(i_projeto, i_caso_teste)
  REFERENCES caso_teste(i_projeto, i_caso_teste)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION   

)

/*
alter tables
*/
alter table projeto alter column report varchar(100)
alter table projeto add column ativo varchar(100)
alter table caso_teste add column parametro clob
alter table caso_teste alter column parametro blob



