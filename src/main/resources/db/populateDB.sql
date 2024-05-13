DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);
INSERT INTO meals (datetime, description, calories, user_id, id)
VALUES ('2021-06-21 10:00', 'Юзер ланч', '510', 100000, 100100),
       ('2021-06-22 00:00', 'Юзер перекус', '200', 100000, 100101),
       ('2021-06-22 10:30', 'Юзер ланч', '350', 100000, 100102),
       ('2021-06-21 10:00', 'Админ ланч', '510', 100001, 100103),
       ('2021-06-21 12:00', 'Админ обед', '600', 100001, 100104),
       ('2021-06-21 18:00', 'Админ ужин', '700', 100001, 100105),
       ('2021-06-22 10:00', 'Админ ланч', '350', 100001, 100106),
       ('2021-06-22 11:00', 'Админ ланч', '450', 100001, 100107),
       ('2021-06-22 13:00', 'Админ обед', '500', 100001, 100108);