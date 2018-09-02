INSERT INTO roles (id, role_name, description, status) VALUES (1, 'STANDARD_USER', 'Standard User - Has no admin rights',1);
INSERT INTO roles (id, role_name, description, status) VALUES (2, 'ADMIN_USER', 'Admin User - Has permission to perform admin tasks',1);

-- USER
-- non-encrypted password: jwtpass
INSERT INTO users (id, first_name, last_name, password, username,status) VALUES (1, 'Cris', 'Ruiz', '821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a', 'cruizg93',1);
INSERT INTO users (id, first_name, last_name, password, username,status) VALUES (2, 'Admin', 'Admin', '821f498d827d4edad2ed0960408a98edceb661d9f34287ceda2962417881231a', 'admin',1);


INSERT INTO user_role(user_id, role_id) VALUES(1,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,1);
INSERT INTO user_role(user_id, role_id) VALUES(2,2);