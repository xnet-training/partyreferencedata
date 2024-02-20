use partyreferencedata;
create table if not exists partyreferencedata.tb_outbox (
  id varchar(32) not null,
  aggregatetype varchar(200) not null,
  aggregateid varchar(32) not null,
  type varchar(60) not null,
  payload jsonb
);
