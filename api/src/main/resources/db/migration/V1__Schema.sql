create table if not exists tb_party (
    id varchar(32) not null,
    created_at datetime not null,
    created_by varchar(32) not null,
    primary key (id)
);

create table if not exists tb_people (
    id varchar(32) not null,
    birth_date date not null,
    created_at datetime not null,
    created_by varchar(32) not null,
    primary key (id)
);
alter table tb_people add constraint fk_party
  foreign key id references tb_party(id);

create table if not exists tb_organization (
    id varchar(32) not null,
    registration_date date not null,
    created_at datetime not null,
    created_by varchar(32) not null,
    primary key (id)
);
alter table tb_people add constraint fk_party
  foreign key id references tb_party(id);

create table if not exists tb_names (
    id varchar(32) not null,
    party_id varchar(32) not null,
    name_function varchar(20) not null, -- First Name, Middle Name, Last Name, Trade Name, Commercial Name
    name_value varchar(60) not null,
    from_date timestamp not null,
    thru_date timestamp null,
    primary key (id)
);
alter table tb_people add constraint fk_party
  foreign key id references tb_party(id);

create table if not exists tb_identification (
    id varchar(32) not null,
    party_id varchar(32) not null,
    kind varchar(20) not null,  -- DNI, CE, PASSPORT, TAX Number
    identification varchar(100) not null,
    from_date timestamp not null,
    thru_date timestamp null,
    primary key (id)    
);
alter table tb_people add constraint fk_party
  foreign key id references tb_party(id);
