/*==============================================================*/
/* Table: API_RECORD(接口调用记录表)                                 */
/*==============================================================*/
create table API_RECORD
(
   ID                   INTEGER not null auto_increment,
   USER_ID              INTEGER,
   METHOD               VARCHAR(60) not null,
   CLIENT_TYPE          VARCHAR(20),
   CLIENT_VERSION       VARCHAR(20),
   CLIENT_VERSION_CODE  INTEGER,
   REQUEST_TIME         DATETIME,
   PROCESS_DURATION     INTEGER,
   IS_SUCCESS           TINYINT(1),
   ERROR_MESSAGE        VARCHAR(100),
   REQUEST_LENGTH       INTEGER,
   RESPONSE_LENGTH      INTEGER,
   primary key (ID)
);