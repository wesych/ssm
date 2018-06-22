/*==============================================================*/
/* Table: UC_MANAGE_USER_ROLE                                   */
/*==============================================================*/
create table UC_MANAGE_USER_ROLE
(
   ID                   INTEGER not null auto_increment,
   USER_ID              INTEGER not null,
   ROLE_ID              VARCHAR(32) not null,
   primary key (ID)
);