CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(100) DEFAULT NULL,
  name varchar(100) DEFAULT NULL,
  status INT(1) NOT NULL,
  PRIMARY KEY (id)
);