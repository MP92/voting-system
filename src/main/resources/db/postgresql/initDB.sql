DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;

DROP SEQUENCE IF EXISTS users_id_seq;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  surname VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  last_voted TIMESTAMP NULL,
  enabled BOOLEAN default TRUE
);
CREATE UNIQUE INDEX users_unique_name_idx ON users(name, surname);

CREATE TABLE roles (
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  role VARCHAR NOT NULL,
  UNIQUE(user_id, role)
);