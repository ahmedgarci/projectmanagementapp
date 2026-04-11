FROM eclipse-temurin:17-jre-alpine
WORKDIR /javaApp
COPY ./target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]