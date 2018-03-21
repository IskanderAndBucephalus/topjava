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
  ('Desayuno1', 500, '1988-08-08 07:00:00', 100000),
  ('Almuerzo1', 100, '1988-08-08 13:00:00', 100000),
  ('Cena1', 500, '1988-08-08 20:00:00', 100000),
  ('Desayuno2', 501, '1988-09-09 07:00:00', 100000),
  ('Almuerzo2', 102, '1988-09-09 13:00:00', 100000),
  ('Cena2', 503, '1988-09-09 20:00:00', 100000),
  ('Desayuno1', 500, '1988-08-08 07:00:00', 100001),
  ('Almuerzo1', 100, '1988-08-08 13:00:00', 100001),
  ('Cena1', 500, '1988-08-08 20:00:00', 100001),
  ('Desayuno2', 501, '1988-09-09 07:00:00', 100001),
  ('Almuerzo2', 102, '1988-09-09 13:00:00', 100001),
  ('Cena2', 503, '1988-09-09 20:00:00', 100001);