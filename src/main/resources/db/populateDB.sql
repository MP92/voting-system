DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 10000 INCREMENT BY 1;

INSERT INTO users(name, surname, password, registered) VALUES ('Admin', 'Adminov', 'admin', '2016-01-01');
INSERT INTO users(name, surname, password, registered) VALUES ('User', 'Userov', 'password', '2016-01-01');
INSERT INTO users(name, surname, password, registered) VALUES ('User2', 'Userov2', 'password2', '2016-01-01');

INSERT INTO roles(user_id, role) VALUES (10000, 'ROLE_ADMIN');
INSERT INTO roles(user_id, role) VALUES (10000, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (10001, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (10002, 'ROLE_USER');

INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10003, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10003, 'Bucatini with Pancetta, Pecorino and Pepper',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10003, 'Cranberry Panna Cotta',
  'This elegant, low-fat panna cotta requires only five ingredients: cranberries, sugar, gelatin, water and buttermilk (instead of the usual cream)',
  200, 'DRINK', 65.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10003, 'Boston Lettuce Salad with Herbs',
  'The herbed vinaigrette here would be lovely on any summer lettuces.',
  65, 'SALAD', 55.8, FALSE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10004, 'Spicy Chickpea Soup',
  'Chickpeas are rich in both types of dietary fiber, which are important for maintaining heart health and for stabilizing blood sugar levels. Pam Anderson uses them as the base for this Indian-flavored creamy (though cream-free) soup, which she prepares by first pureeing it, then simmering it, to save time. "Pureed beans give you richness without having to enrich the soup," she says.',
  265, 'SOUP', 150.5, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10004, 'Chicken in Tarragon-Mustard Cream Sauce',
  'F&W''s Grace Parisi steals the flavors from a classic French pan sauce (mustard, tarragon, white wine and cream) for this quick chicken saute.',
  230, 'GARNISH', 95.5, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10004, 'Avocado, Orange and Jicama Salad',
  'Feta is a fun, briny addition to this salad''s Mexican mix of jicama, avocado and cilantro.',
  70, 'SALAD', 65.5, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10004, 'Равиоли из ананаса с тайским манго и лаймовым сорбетом',
  'Долго смотреть на этот десерт невозможно - он нереально, ну просто фантастически летний. Тончайшие ломтики ананасовой мякоти выполняют роль теста в классических равиоли. Внутри – нарезанное мелкими кубиками манго с соусом из лайма.',
  125, 'DESSERT', 245.5, FALSE);

INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10000, 10003, now()::date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10001, 10004, now()::date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10002, NULL, NULL);

ALTER SEQUENCE global_seq INCREMENT BY 50;
