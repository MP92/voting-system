DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS restaurants CASCADE;
DROP TABLE IF EXISTS dishes CASCADE;
DROP TABLE IF EXISTS menus;
DROP TABLE IF EXISTS votes;

DROP SEQUENCE IF EXISTS users_id_seq;
DROP SEQUENCE IF EXISTS restaurants_id_seq;
DROP SEQUENCE IF EXISTS dishes_id_seq;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  surname VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  registered TIMESTAMP DEFAULT now(),
  last_voted TIMESTAMP NULL,
  enabled BOOLEAN DEFAULT TRUE
);
CREATE UNIQUE INDEX users_unique_name_idx ON users(name, surname);

CREATE TABLE roles (
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
  role VARCHAR NOT NULL,
  UNIQUE(user_id, role)
);

CREATE TABLE restaurants (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  description VARCHAR NULL,
  address VARCHAR NOT NULL,
  phone_number VARCHAR NOT NULL
);
CREATE UNIQUE INDEX restaurant_unique_name_idx ON restaurants(name);

CREATE TABLE dishes (
  id SERIAL PRIMARY KEY,
  restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE CASCADE,
  name VARCHAR NOT NULL,
  description VARCHAR NOT NULL,
  weight INTEGER NOT NULL,
  category VARCHAR NOT NULL,
  price NUMERIC(7, 2) NOT NULL,
  UNIQUE(restaurant_id, name)
);

CREATE TABLE menus (
  restaurant_id INTEGER REFERENCES restaurants(id) ON DELETE CASCADE,
  dish_id INTEGER REFERENCES dishes(id) ON DELETE CASCADE,
  UNIQUE(restaurant_id, dish_id)
);

CREATE TABLE votes (
  restaurant_id INTEGER UNIQUE REFERENCES restaurants(id) ON DELETE CASCADE,
  count INTEGER NOT NULL
);