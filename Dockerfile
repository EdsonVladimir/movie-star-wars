# Usamos una imagen base de OpenJDK 8
FROM openjdk:8-jdk-alpine

# Definimos el directorio de trabajo en el contenedor
WORKDIR /app

# Copiamos el archivo JAR del proyecto a la imagen
COPY target/movie-star-wars.jar /app/movie-star-wars.jar

# Exponemos el puerto que la aplicación usará
EXPOSE 3000

# Comando para ejecutar la aplicación
CMD ["java", "-Xmx450m", "-Xms256m", "-jar", "movie-star-wars.jar"]
