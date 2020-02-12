INSERT INTO regiones(id,nombre)VALUES(1,'Armenia');
INSERT INTO regiones(id,nombre)VALUES(2,'Medellin');
INSERT INTO regiones(id,nombre)VALUES(3,'Cartagena');
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo','Henao','mateohr880@gmail.com','2018-01-01',1);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo2','Henao2','mateohr2880@gmail.com','2018-01-01',2);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo3','Henao3','mateohr3880@gmail.com','2018-01-01',3);
--creando usuarios con sus roles--
INSERT INTO `usuarios` (username,password,enabled) VALUES ('andres','$2a$10$igyQApzP7pNJ9E3Ns/skz.d6i1M/.NIlMByiZZ4rDgNV2gInLNGGm
',1);
INSERT INTO `usuarios` (username,password,enabled) VALUES ('amin','$2a$10$W49sdMHj4I/Xw/I3YapD3OER7aa0VTZIj33KQCKhiRw2ifoQlqdPC
',1);

INSERT INTO `roles` (nombre) VALUES ('ROL_USUARIO');
INSERT INTO `roles` (nombre) VALUES ('ROL_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (1,1);
INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (2,2);
INSERT INTO `usuarios_roles` (usuario_id,rol_id) VALUES (2,1);