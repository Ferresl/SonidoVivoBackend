FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /proyecto
COPY . .
ARG SERVICE
RUN mvn -B -pl ${SERVICE} -am package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
ARG SERVICE
COPY --from=build /proyecto/${SERVICE}/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
