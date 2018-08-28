CREATE TABLE 'virtualbook'.'clients' (
  'id' INT NOT NULL AUTO_INCREMENT,
  'name' VARCHAR(45) NOT NULL,
  'email' VARCHAR(45) NULL,
  'phone_number' VARCHAR(45) NULL,
  'address' VARCHAR(45) NULL,
  'tax_id' VARCHAR(45) NULL,
  'status' INT NOT NULL,
  PRIMARY KEY ('id'),
  UNIQUE INDEX 'phone_number_UNIQUE' ('phone_number' ASC),
  UNIQUE INDEX 'email_UNIQUE' ('email' ASC),
  UNIQUE INDEX 'tax_id_UNIQUE' ('tax_id' ASC));
