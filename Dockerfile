# Usamos una imagen base de OpenJDK 8
FROM openjdk:8-jdk-alpine

# Definimos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR del proyecto a la imagen
COPY target/movie-star-wars.jar /app/omovie-star-wars.jar

# Exponemos el puerto que la aplicación usará
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "movie-star-wars.jar"]
