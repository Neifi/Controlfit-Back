
# GestionGym API

API para la gestion de clientes y usuarios de un gimnasio
 ## Tecnologías
* Sprig web 
* Spring security
* Swagger
    

# Preparación del entorno

* Base de datos usada postgres: https://www.postgresql.org/download/
    * Script de las tablas en src/main/resources/gym.sql
    
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
Para probar las peticiones con postman sera necesaria la autenticación del cliente 
haciendo un post a localhost:8081/auth/login el cuerpo de la peticion debera ser el usuario
y password en formato json.
### Usuario admin
>Usuario: admin
>Password: admin

### Usuario normal
>Usuario:user
  Password:user

#### Usuario no verificado
> Usuario: unverified
>Password: unverified
## Swagger

Para probar las peticiones sin postman, en el navegador se pondra:
http://[host]:[puerto]/swagger-ui.html

por ejemplo:
http://localhost:8081/swagger-ui.html

Las credenciales de acceso son las mencionadas anteriormente.

# Controlfit-Back
API Java
