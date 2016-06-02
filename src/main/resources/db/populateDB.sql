DELETE FROM users;

ALTER SEQUENCE users_id_seq RESTART WITH 1;

INSERT INTO users (name, surname, password, registered, last_voted) VALUES ('Admin', 'Adminov', 'admin', '2016-01-01', '2016-01-01');
INSERT INTO users (name, surname, password, registered, last_voted) VALUES ('User', 'Userov', 'user', '2016-01-01', '2016-01-01');

INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (2, 'ROLE_USER');