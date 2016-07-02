DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS votes;

DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 10000;

CREATE TABLE users (
  id         INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name       VARCHAR   NOT NULL,
  surname    VARCHAR   NOT NULL,
  password   VARCHAR   NOT NULL,
  registered TIMESTAMP NOT NULL  DEFAULT now(),
  enabled    BOOLEAN   NOT NULL  DEFAULT TRUE
);
CREATE UNIQUE INDEX users_unique_name_idx ON users (name);

CREATE TABLE roles (
  user_id INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  role    VARCHAR NOT NULL,
  UNIQUE (user_id, role)
);

CREATE TABLE restaurants (
  id           INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name         VARCHAR NOT NULL,
  description  VARCHAR NOT NULL,
  address      VARCHAR NOT NULL,
  phone_number VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants (name);

CREATE TABLE dishes (
  id            INTEGER PRIMARY KEY    DEFAULT nextval('global_seq'),
  restaurant_id INTEGER       NOT NULL REFERENCES restaurants (id) ON DELETE CASCADE,
  name          VARCHAR       NOT NULL,
  description   VARCHAR       NOT NULL,
  weight        INTEGER       NOT NULL,
  category      VARCHAR       NOT NULL,
  price         NUMERIC(7, 2) NOT NULL,
  in_menu       BOOLEAN       NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX dishes_unique_name_idx ON dishes (restaurant_id, name);

CREATE TABLE votes (
  user_id       INTEGER NOT NULL PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
  restaurant_id INTEGER REFERENCES restaurants (id) ON DELETE SET NULL,
  last_voted    TIMESTAMP DEFAULT now()
);