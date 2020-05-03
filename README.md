# GestionGym API

API para la gestion de clientes y usuarios de un gimnasio
 ## Tecnologías
* Sprig web 
* Spring security
* Swagger
    

# Preparación del entorno

* Base de datos usada postgres: https://www.postgresql.org/download/
    * Script de las tablas en src/main/resources/
    
    * Por defecto la aplicación se ejecuta en localhost:8081
    * La base de datos en el puerto 5432

 Par cambiar estas propiedes, ir al archivo src/main/resources/aplication.properties
```sh
    spring.datasource.url=jdbc:postgresql://[host]:[puerto]/gestiongym
    spring.datasource.username=[nombre de usuario]
    spring.datasource.password=[password]
    server.port=[Puerto]
```

## Postman
Para probar las peticiones con postman sera necesaria la autenticación del cliente.
### Usuario admin
>Usuario: admin
Password: admin
