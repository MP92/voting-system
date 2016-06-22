DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS votes;
DROP TABLE IF EXISTS voting_statistics;

DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS restaurants_id_seq;
DROP SEQUENCE IF EXISTS dishes_id_seq;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  surname VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  registered TIMESTAMP NOT NULL DEFAULT now(),
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE UNIQUE INDEX users_unique_name_idx ON users(name, surname);

CREATE TABLE roles (
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  role VARCHAR NOT NULL,
  UNIQUE(user_id, role)
);

CREATE TABLE restaurants (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  address VARCHAR NOT NULL,
  phone_number VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants(name);

CREATE TABLE dishes (
  id SERIAL PRIMARY KEY,
  restaurant_id INTEGER NOT NULL REFERENCES restaurants(id) ON DELETE CASCADE,
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  weight INTEGER NOT NULL,
  category VARCHAR NOT NULL,
  price NUMERIC(7, 2) NOT NULL,
  in_menu BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX dishes_unique_name_idx ON dishes(restaurant_id, name);

CREATE TABLE votes (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE SET NULL,
  last_voted TIMESTAMP DEFAULT now(),
  UNIQUE(user_id, restaurant_id)
);

CREATE TABLE voting_statistics (
  restaurant_id INTEGER NOT NULL UNIQUE REFERENCES restaurants(id) ON DELETE CASCADE,
  votes INTEGER NOT NULL
);