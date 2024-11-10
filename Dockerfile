# Etapa 1: Construcción
FROM maven:3.8.7-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/movie-star-wars.jar /app/movie-star-wars.jar
EXPOSE 8080
CMD ["java", "-jar", "movie-star-wars.jar"]