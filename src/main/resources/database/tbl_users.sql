CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  email varchar(255),
  phone_number varchar(50),
  status int(1) NOT NULL,
  created_at DATETIME() NOT NULL,
  updated_at DATETIME(),
  PRIMARY KEY (id)
);