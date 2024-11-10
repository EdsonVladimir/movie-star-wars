# Usamos una imagen base de Maven para construir el JAR
FROM maven:3.6-jdk-8-alpine as builder

# Definimos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo pom.xml y descargamos las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y construimos el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Usamos la imagen de OpenJDK 8 para ejecutar la aplicación
FROM openjdk:8-jdk-alpine

# Definimos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el JAR generado desde la fase de construcción
COPY --from=builder /app/target/movie-star-wars.jar /app/movie-star-wars.jar

# Exponemos el puerto que la aplicación usará
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "movie-star-wars.jar"]
