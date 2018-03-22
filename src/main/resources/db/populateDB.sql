DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, calories, datetime, user_ref) VALUES
  ('Desayuno01', 500, '1988-08-08 07:00:00', 100000),
  ('Almuerzo01', 100, '1988-08-08 13:00:00', 100000),
  ('Cena01', 500, '1988-08-08 20:00:00', 100000),
  ('Desayuno02', 501, '1988-09-09 07:00:00', 100000),
  ('Almuerzo02', 102, '1988-09-09 13:00:00', 100000),
  ('Cena02', 503, '1988-09-09 20:00:00', 100000),
  ('Desayuno11', 500, '1988-08-08 07:00:00', 100001),
  ('Almuerzo11', 100, '1988-08-08 13:00:00', 100001),
  ('Cena11', 500, '1988-08-08 20:00:00', 100001),
  ('Desayuno12', 501, '1988-09-09 07:00:00', 100001),
  ('Almuerzo12', 102, '1988-09-09 13:00:00', 100001),
  ('Cena12', 503, '1988-09-09 20:00:00', 100001);