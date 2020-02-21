INSERT INTO regiones(id,nombre)VALUES(1,'Armenia');
INSERT INTO regiones(id,nombre)VALUES(2,'Medellin');
INSERT INTO regiones(id,nombre)VALUES(3,'Cartagena');
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo','Henao','mateohr880@gmail.com','2018-01-01',1);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo2','Henao2','mateohr2880@gmail.com','2018-01-01',2);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo3','Henao3','mateohr3880@gmail.com','2018-01-01',3);
INSERT INTO `roles`(nombre) VALUES ('ROL_USUARIO');
INSERT INTO `roles`(nombre) VALUES ('ROL_ADMIN');
INSERT INTO `usuarios` (username,password,enabled,nombre,apellido,email) VALUES ('andres',
'$2a$10$ccdQFpkXfffkcFa7K/idYeP6w5r5IwlTjZbwlAQFKoJttCGIm1hka',1,'Andres','Guzman','email1@gmail.coma');
INSERT INTO `usuarios` (username,password,enabled,nombre,apellido,email) VALUES ('mateo'
,'$2a$10$6mRXKFtvkHcM9wMuQHBrmeZFZGlV9x0HJDfOgs0tl5kWFEDGlR9qm',1,'teo','Guzman','emailadssa1@gmail.coma');
INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (1,1);
INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (2,1);
INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (2,2);