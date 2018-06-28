/*==============================================================*/
/* Table: UC_USER                                  */
/*==============================================================*/
CREATE TABLE uc_user  (
  user_id Integer(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  account varchar(255) NOT NULL,
  nickname varchar(255) NULL DEFAULT NULL,
  mobile varchar(20) NOT NULL,
  password varchar(255) NOT NULL,
  email varchar(255) DEFAULT NULL,
  gender tinyint(1) NULL DEFAULT 1,
  city varchar(255)  DEFAULT NULL,
  birth date NULL DEFAULT NULL,
  join_time date NOT NULL,
  locked tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
);