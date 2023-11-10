CREATE TABLE gimnasio
(
    id_gimnasio   INT PRIMARY KEY AUTO_INCREMENT,
    nombre        varchar(50),
    ciudad        VARCHAR(50),
    direccion     VARCHAR(50),
    codigo_postal INT,
    provincia     VARCHAR(50),
    pais          VARCHAR(50)
);
CREATE TABLE customer_role
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    rol         VARCHAR NOT NULL
);

CREATE TABLE customer
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    gym_id      INT,
    username    VARCHAR     NOT NULL UNIQUE,
    password    VARCHAR     NOT NULL,
    avatar      VARCHAR,
    created_at  VARCHAR,
    updated_at  VARCHAR,
    verified    BOOLEAN     NOT NULL,
    dni         VARCHAR(9) UNIQUE,
    first_name  VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    birth_date  VARCHAR(10) NOT NULL,
    email       VARCHAR UNIQUE,
    street      VARCHAR,
    postal_code VARCHAR,
    city        VARCHAR,
    province    VARCHAR

);


CREATE TABLE metodoPago
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente         INT,
    codigoConfirmacion INT,
    numeroTarjeta      INT,
    IBAN               VARCHAR,
    fecha_caducidad    DATE,
    nombreTitular      VARCHAR

);
CREATE TABLE factura
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    iva             INT NOT NULL,
    importeUnitario INT NOT NULL

);
CREATE TABLE telefono
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente INT,
    numero     varchar NOT NULL
);
CREATE TABLE rutinaActiva
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR NOT NULL,
    descripcion VARCHAR NOT NULL,
    progreso    INT     NOT NULL

);
CREATE TABLE ejercicio
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    id_rutina  INT REFERENCES rutinaActiva (id),
    nombre     VARCHAR NOT NULL,
    dificultad INT     NOT NULL
);

CREATE TABLE registrohorario
(
    id_registrohorario INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario         INT,
    horaEntrada        VARCHAR,
    horaSalida         VARCHAR,
    fecha              VARCHAR
);



CREATE TABLE verification_token
(
    id_token   INT PRIMARY KEY AUTO_INCREMENT,
    token      varchar,
    expiryDate DATE

);



insert into public.gimnasio (direccion, codigo_postal, pais, ciudad, nombre, provincia)
values ('Passeig de pere III 92', 08241, 'España', 'Manresa', 'Manresa1', 'Barcelona');

insert into public.customer_role( rol)
values ('ADMIN');
insert into public.customer_role( rol)
values ( 'USER');
insert into public.customer_role( rol)
values ( 'UNVERIFIED');

INSERT INTO customer (gym_id,
                      username,
                      password,
                      avatar,
                      created_at,
                      updated_at,
                      verified,
                      dni,
                      first_name,
                      last_name,
                      birth_date,
                      email,
                      street,
                      postal_code,
                      city,
                      province
                      )
VALUES (1,
        'admin',
        '$2b$12$EsN3kf6DM7iFNcKPjv36keMH/JPNK7DAbgpxpuKZqjqK4NJnE9GX2',
        'default_avatar.jpg',
        '2023-11-10 00:00:00',
        '2023-11-10 00:00:00',
        FALSE,
        '99809870J',
        'John Doe',
        'Smith',
        '1990-01-01',
        'johndoe@example.com',
        '123 Main St',
        '12345', --
        'Anytown',
        'Anystate'
       );



