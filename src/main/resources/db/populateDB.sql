DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM menus;
DELETE FROM votes;
DELETE FROM voting_statistics;

ALTER SEQUENCE users_id_seq RESTART WITH 1;
ALTER SEQUENCE restaurants_id_seq RESTART WITH 100;
ALTER SEQUENCE dishes_id_seq RESTART WITH 1000;

INSERT INTO users(name, surname, password, registered, last_voted) VALUES ('Admin', 'Adminov', 'admin', '2016-01-01', '2016-01-01');
INSERT INTO users(name, surname, password, registered, last_voted) VALUES ('User', 'Userov', 'user', '2016-01-01', '2016-01-01');
INSERT INTO users(name, surname, password, registered, last_voted) VALUES ('User2', 'Userov2', 'user2', '2016-01-01', '2016-01-01');

INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (2, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (3, 'ROLE_USER');

INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  100, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  100, 'Bucatini with Pancetta, Pecorino and Pepper',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  100, 'Cranberry Panna Cotta',
  'This elegant, low-fat panna cotta requires only five ingredients: cranberries, sugar, gelatin, water and buttermilk (instead of the usual cream)',
  200, 'DRINK', 65.4);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  100, 'Boston Lettuce Salad with Herbs',
  'The herbed vinaigrette here would be lovely on any summer lettuces.',
  65, 'SALAD', 55.8);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  101, 'Spicy Chickpea Soup',
  'Chickpeas are rich in both types of dietary fiber, which are important for maintaining heart health and for stabilizing blood sugar levels. Pam Anderson uses them as the base for this Indian-flavored creamy (though cream-free) soup, which she prepares by first pureeing it, then simmering it, to save time. "Pureed beans give you richness without having to enrich the soup," she says.',
  265, 'SOUP', 150.5);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  101, 'Chicken in Tarragon-Mustard Cream Sauce',
  'F&W''s Grace Parisi steals the flavors from a classic French pan sauce (mustard, tarragon, white wine and cream) for this quick chicken saute.',
  230, 'GARNISH', 95.5);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  101, 'Avocado, Orange and Jicama Salad',
  'Feta is a fun, briny addition to this salad''s Mexican mix of jicama, avocado and cilantro.',
  70, 'SALAD', 65.5);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price) VALUES (
  101, 'Gianduja Mousse',
  'As if the chocolate-hazelnut spread gianduja isn''t delicious enough straight off the spoon, Grace Parisi has folded in whipped cream and creme fraiche to create a truly decadent (and ridiculously easy) mousse. For a supereasy ice cream sandwich, spoon the mousse between chocolate wafers and freeze overnight.',
  110, 'DESSERT', 175.5);

INSERT INTO menus(restaurant_id, dish_id) VALUES (100, 1000);
INSERT INTO menus(restaurant_id, dish_id) VALUES (100, 1001);
INSERT INTO menus(restaurant_id, dish_id) VALUES (100, 1002);

INSERT INTO menus(restaurant_id, dish_id) VALUES (101, 1004);
INSERT INTO menus(restaurant_id, dish_id) VALUES (101, 1005);
INSERT INTO menus(restaurant_id, dish_id) VALUES (101, 1006);

INSERT INTO votes(user_id, restaurant_id, date_time) VALUES (1, 100, '2016-01-01');
INSERT INTO votes(user_id, restaurant_id, date_time) VALUES (2, 101, '2016-01-01');

INSERT INTO voting_statistics(restaurant_id, votes) VALUES (100, 1);
INSERT INTO voting_statistics(restaurant_id, votes) VALUES (101, 1);