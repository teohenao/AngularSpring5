INSERT INTO regiones(id,nombre)VALUES(1,'Armenia');
INSERT INTO regiones(id,nombre)VALUES(2,'Medellin');
INSERT INTO regiones(id,nombre)VALUES(3,'Cartagena');
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo','Henao','mateohr880@gmail.com','2018-01-01',1);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo2','Henao2','mateohr2880@gmail.com','2018-01-01',2);
INSERT INTO clientes(nombre,apellido,email,create_at,region_id)VALUES('Mateo3','Henao3','mateohr3880@gmail.com','2018-01-01',3);
INSERT INTO `roles`(nombre) VALUES ('ROLE_ADMIN');
INSERT INTO `roles`(nombre) VALUES ('ROLE_USUARIO');
INSERT INTO `usuarios` (username,password,enabled,nombre,apellido,email) VALUES ('andres',
'$2a$10$ccdQFpkXfffkcFa7K/idYeP6w5r5IwlTjZbwlAQFKoJttCGIm1hka',1,'Andres','Guzman','email1@gmail.coma');
INSERT INTO `usuarios` (username,password,enabled,nombre,apellido,email) VALUES ('mateo'
,'$2a$10$6mRXKFtvkHcM9wMuQHBrmeZFZGlV9x0HJDfOgs0tl5kWFEDGlR9qm',1,'teo','Guzman','emailadssa1@gmail.coma');
INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES (1,2);
INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES (2,2);
INSERT INTO `usuarios_roles` (usuario_id,role_id) VALUES (2,1);

/* Populate tabla productos */
INSERT INTO productos (nombre, precio, create_at) VALUES('Panasonic Pantalla LCD', 259990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Camara digital DSC-W320B', 123490, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Apple iPod shuffle', 1499990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Sony Notebook Z110', 37990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Hewlett Packard Multifuncional F2280', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Bianchi Bicicleta Aro 26', 69990, NOW());
INSERT INTO productos (nombre, precio, create_at) VALUES('Mica Comoda 5 Cajones', 299990, NOW());

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());

INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

