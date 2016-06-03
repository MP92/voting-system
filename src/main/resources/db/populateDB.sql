DELETE FROM users;

ALTER SEQUENCE users_id_seq RESTART WITH 1;

INSERT INTO users(name, surname, password, registered, last_voted) VALUES ('Admin', 'Adminov', 'admin', '2016-01-01', '2016-01-01');
INSERT INTO users(name, surname, password, registered, last_voted) VALUES ('User', 'Userov', 'user', '2016-01-01', '2016-01-01');

INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles(user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (2, 'ROLE_USER');

DELETE FROM restaurants;

ALTER SEQUENCE restaurants_id_seq RESTART WITH 100;

INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');

ALTER SEQUENCE dishes_id_seq RESTART WITH 1000;

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  100, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapeños and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  100, 'Bucatini with Pancetta, Pecorino and Pepper',
  '“I love knowing that I have leftovers in the refrigerator,” says chef Shelley Lindgren. She’ll often make pasta during the day—like this rich, pancetta-studded bucatini that’s tossed with plenty of freshly ground black pepper and Pecorino—and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  100, 'Cranberry Panna Cotta',
  'This elegant, low-fat panna cotta requires only five ingredients: cranberries, sugar, gelatin, water and buttermilk (instead of the usual cream)',
  200, 'DRINK', 65.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  101, 'Spicy Chickpea Soup',
  'Chickpeas are rich in both types of dietary fiber, which are important for maintaining heart health and for stabilizing blood sugar levels. Pam Anderson uses them as the base for this Indian-flavored creamy (though cream-free) soup, which she prepares by first pureeing it, then simmering it, to save time. "Pureed beans give you richness without having to enrich the soup," she says.',
  265, 'SOUP', 150.5, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  101, 'Chicken in Tarragon-Mustard Cream Sauce',
  'F&W''s Grace Parisi steals the flavors from a classic French pan sauce (mustard, tarragon, white wine and cream) for this quick chicken sauté.',
  230, 'GARNISH', 95.5, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, menu_item) VALUES (
  101, 'Gianduja Mousse',
  'As if the chocolate-hazelnut spread gianduja isn''t delicious enough straight off the spoon, Grace Parisi has folded in whipped cream and crème fraîche to create a truly decadent (and ridiculously easy) mousse. For a supereasy ice cream sandwich, spoon the mousse between chocolate wafers and freeze overnight.',
  110, 'DESSERT', 175.5, TRUE);