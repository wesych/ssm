/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table user  (
  user_id integer(11) unsigned not null auto_increment,
  account varchar(255) not null,
  nickname varchar(255),
  mobile varchar(20) not null,
  password varchar(255) not null,
  email varchar(255),
  gender tinyint(1) default 1,
  city varchar(255),
  birth date,
  join_time date not null,
  locked tinyint(1) default 0,
  primary key (user_id) using btree,
  unique index `mobile`(`mobile`) using btree,
  unique index `account`(`account`) using btree,
  unique index `email`(`email`) using btree
);


/*==============================================================*/
/* Table: ROLE                                                  */
/*==============================================================*/
create table role (
  role_id  integer(11) unsigned not null auto_increment,
  role_name varchar(30) not null,
  create_time date not null,
  primary key (role_id)
);

/*==============================================================*/
/* Table: PERMISSION                                            */
/*==============================================================*/
create table permission (
  permission_id  integer(11) unsigned not null auto_increment,
  permission_name varchar(30) not null,
  create_time date not null,
  primary key (permission_id)
);

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
create table user_role (
  id  integer(11) unsigned not null auto_increment,
  user_id integer(11) not null,
  role_id integer(11) not null,
  primary key (id)
);

/*==============================================================*/
/* Table: ROLE_PERMISSION                                       */
/*==============================================================*/
create table role_permission (
  id  integer(11) unsigned not null auto_increment,
  role_id integer(11) not null,
  permission_id integer(11) not null,
  primary key (id)
);