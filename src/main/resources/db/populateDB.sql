DELETE FROM users;
DELETE FROM roles;
DELETE FROM restaurants;
DELETE FROM dishes;
DELETE FROM votes;

ALTER SEQUENCE global_seq RESTART WITH 10000 INCREMENT BY 1;

INSERT INTO users(name, surname, password, registered) VALUES ('Admin', 'Adminov', '$2a$10$K1O0YHPJL4VPKmcikU6UDOI/PoSvVkaBQPBAOs4iQSAqbUcZIRoiW', '2016-01-01');
INSERT INTO users(name, surname, password, registered) VALUES ('User', 'Userov', '$2a$10$78hzzIImUTjpLYkM9Zr67.rvYxHcsar6btOKRpsSyVmDwND4/HZGC', '2016-01-01');
INSERT INTO users(name, surname, password, registered) VALUES ('User2', 'Userov2', '$2a$10$q/yxIJzhiIQusyvYPhFlqeW5n/Fk0DxBfnr2vy1.S29RShLHe.K76', '2016-01-01');

INSERT INTO roles(user_id, role) VALUES (10000, 'ROLE_ADMIN');
INSERT INTO roles(user_id, role) VALUES (10000, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (10001, 'ROLE_USER');
INSERT INTO roles(user_id, role) VALUES (10002, 'ROLE_USER');

INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci2', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci3', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci4', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('E Pellicci5', 'E Pellicci description', '332 Bethnal Green Rd, London E2 0AG, England', '+44 20 7739 4873');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia2', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia3', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia4', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');
INSERT INTO restaurants(name, description, address, phone_number) VALUES ('taNgia5', 'taNgia description', '108 Mitcham Road | Tooting Broadway, London SW17 9NG, England', '+44 20 3774 0779');

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

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10005, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup2',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10005, 'Bucatini with Pancetta, Pecorino and Pepper2',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10006, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup3',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10006, 'Bucatini with Pancetta, Pecorino and Pepper3',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10007, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup4',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10007, 'Bucatini with Pancetta, Pecorino and Pepper4',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10008, 'Beer-and-Cheddar SoupBeer-and-Cheddar Soup5',
  'When Jonathon Erdeljac opened his new restaurant, Jonathon''s Oak Cliff, in Dallas, he knew he wanted to serve this rich soup. It''s a favorite of his, especially with jalapenos and smoky bacon stirred in.',
  260, 'SOUP', 84.6, TRUE);

INSERT INTO dishes(restaurant_id, name, description, weight, category, price, in_menu) VALUES (
  10008, 'Bucatini with Pancetta, Pecorino and Pepper5',
  '"I love knowing that I have leftovers in the refrigerator," says chef Shelley Lindgren. She''ll often make pasta during the day like this rich, pancetta-studded bucatini that''s tossed with plenty of freshly ground black pepper and Pecorino and then reheat a big bowl of it when she comes home from work late at night.',
  250, 'GARNISH', 75.4, TRUE);

INSERT INTO users(name, surname, password, registered) VALUES ('User3', 'Userov3', '$2a$10$uH3APhL2UVXb.t8LpsKWFeNWSS6tbE2dhIJwnoCto1STfkOlkfqhm', '2016-01-01'); -- id=10029
INSERT INTO users(name, surname, password, registered) VALUES ('User4', 'Userov4', '$2a$10$8AXVXHiVGmE55ZcjdYs9E.wXLXgG6O222997y3omttCaMNB.NGQKm', '2016-01-01'); -- id=10030
INSERT INTO users(name, surname, password, registered) VALUES ('User5', 'Userov5', '$2a$10$.pW/TGdQ4FZH3h7mimCMbOP96UItqb1eFEjw0pBTy7j81CS3EhVoi', '2016-01-01'); -- id=10031
INSERT INTO users(name, surname, password, registered) VALUES ('User6', 'Userov6', '$2a$10$a9OFya26MZFkPP9vQDB0te4MJeaXNJ7kCOYJ6ZRokYEUsRnbCkfkS', '2016-01-01'); -- id=10032
INSERT INTO users(name, surname, password, registered) VALUES ('User7', 'Userov7', '$2a$10$6lk4Gbv01ru/DjzqznbFB.5FmBG0Bb1BNIsIAeqSnfC8uu05niz/m', '2016-01-01'); -- id=10033
INSERT INTO users(name, surname, password, registered) VALUES ('User8', 'Userov8', '$2a$10$MeuqeqYxq1sRl5o3wme/mu2RgNHUT.Re9O7Z5As5i9qF6XvmiSabC', '2016-01-01'); -- id=10034
INSERT INTO users(name, surname, password, registered) VALUES ('User9', 'Userov9', '$2a$10$t3PUoJt1FFlTpxd0l5S5ZuovAr9FGj.4xQHoH2.6Uhsc3fcdxIJmG', '2016-01-01'); -- id=10035

INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10000, 10003, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10001, 10004, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10002, 10004, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10029, 10004, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10030, 10004, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10031, 10005, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10032, 10005, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10033, 10005, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10034, 10006, current_date);
INSERT INTO votes(user_id, restaurant_id, last_voted) VALUES (10035, 10008, current_date);

ALTER SEQUENCE global_seq INCREMENT BY 50;