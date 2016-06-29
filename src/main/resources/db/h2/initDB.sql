DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS votes;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 10000;

CREATE TABLE users (
  id         INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  name       VARCHAR_IGNORECASE      NOT NULL,
  surname    VARCHAR_IGNORECASE      NOT NULL,
  password   VARCHAR_IGNORECASE      NOT NULL,
  registered TIMESTAMP DEFAULT now() NOT NULL,
  enabled    BOOLEAN DEFAULT TRUE    NOT NULL
);
CREATE UNIQUE INDEX users_unique_name_idx ON users (name, surname);

CREATE TABLE roles (
  user_id INTEGER            NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  role    VARCHAR_IGNORECASE NOT NULL,
  UNIQUE (user_id, role)
);

CREATE TABLE restaurants (
  id           INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  name         VARCHAR NOT NULL,
  description  VARCHAR NOT NULL,
  address      VARCHAR NOT NULL,
  phone_number VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants (name);

CREATE TABLE dishes (
  id            INTEGER DEFAULT global_seq.nextval PRIMARY KEY,
  restaurant_id INTEGER               NOT NULL REFERENCES restaurants (id) ON DELETE CASCADE,
  name          VARCHAR               NOT NULL,
  description   VARCHAR               NOT NULL,
  weight        INTEGER               NOT NULL,
  category      VARCHAR               NOT NULL,
  price         NUMERIC(7, 2)         NOT NULL,
  in_menu       BOOLEAN DEFAULT FALSE NOT NULL
);
CREATE UNIQUE INDEX dishes_unique_name_idx ON dishes (restaurant_id, name);

CREATE TABLE votes (
  user_id       INTEGER NOT NULL PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
  restaurant_id INTEGER REFERENCES restaurants (id) ON DELETE SET NULL,
  last_voted    TIMESTAMP DEFAULT now()
);