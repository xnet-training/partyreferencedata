create table if not exists tb_iddocument(
  id varchar(32) not null,
  party_id varchar(32) not null,
  kind int not null,
  value varchar(20) not null,
  primary key(id)
);
alter table tb_iddocument add constraint fk_party
  foreign key (party_id) references tb_party(id);

create table if not exists tb_contact(
  id varchar(32) not null,
  party_id varchar(32) not null,
  kind int not null,
  value varchar(200) not null,
  country_code varchar(10) null,
  from_date timestamp not null,
  priority int default 0,
  thru_date timestamp null,
  primary key (id)
);
alter table tb_contact add constraint fk_party
  foreign key (party_id) references tb_party(id);
