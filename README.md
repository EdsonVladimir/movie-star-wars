
# MOVIE STAR WARS - JAVA 8

aplicación en Java8(Spring Boot) que se integra con la API de Star Wars (https://www.swapi.tech/documentation). La aplicación lista People, Films, Starships y Vehicles de manera paginada, filtra por ID y/o nombre, y cuenta con un sistema de autenticación seguro implementado con JWT. Además, la aplicación esta documentada en swagger y conteniene pruebas unitarias y de integración.

### 
## Contenido
- [Características](#Características)
- [Instalacion](#Instalacion)
- [Configuracion](#Configuracion)
- [Uso](#Uso)
- [API Documentation](#api-documentation)
- [Generar -jar](#running-tests)
- [Running Tests](#running-tests)

## Características
- Creación, busqueda por id y busqueda por nombre de usuarios
- Login de usuario para acceder a las siguientes busquedas
- Búsqueda y filtrada filtrada por pagina, id, nombre de Film
- Búsqueda y filtrada filtrada por pagina, id, nombre de People
- Búsqueda y filtrada filtrada por pagina, id, nombre de Starshep
- Búsqueda y filtrada filtrada por pagina, id, nombre de Vehicles
- Sincronización en tiempo real
- Roles y permisos para usuarios

## Instalacion
1. Clonar del repositorio:
   ```bash
   git clone https://github.com/EdsonVladimir/movie-star-wars.git
   cd repo

2. Start application
    2.1 docker-compose
        - ``bash docker-compose up -d || docker-compose up -d postgres && docker-compose up -d pgadmin && (antes obtener la info y la ip de la base de datos en postgres bash docker inspect 286d02a5a465 obtenemos el host de nuestra base de datos)docker-compose up --build app
    2.2 maven en bash sobre la rais del proyecto ejecutar "mvn spring-boot:run" 

## Configuracion 
1. crear la base de datos en protgres >=13 con los siguientes datos 
    1.1 CREATE TABLE public.app_user
(
    id bigint NOT NULL DEFAULT nextval('app_user_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    enabled boolean,
    CONSTRAINT app_user_pkey PRIMARY KEY (id)
) si el trabajo es local.

    1.2 usar la base de datos test en produccion 
        - DB_URL=jdbc:postgresql://autorack.proxy.rlwy.net:19960/railway
        - DB_USERNAME=postgres
        - DB_PASSWORD=AVMhWKnbzJVecYLntkVuFjNuGaJyejGf
        - DB_NAME=railway


##Uso
Local http://localhost:8080/swagger-ui/index.html
Publicado https://movie-star-wars-gybt.onrender.com/swagger-ui/index.html

## Api Documentacion
Local http://localhost:8080/swagger-ui/index.html
Publicado https://movie-star-wars-gybt.onrender.com/swagger-ui/index.html


## Running Tests
    mvn clean package -DskipTests   
    /target/mi-app.jar

## Running Tests
    mvn test || mvn -Dtest=UserServiceTest test

