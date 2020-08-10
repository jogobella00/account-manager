FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build
COPY src /build/src
RUN mvn -B -f pom.xml clean package -DskipTests

FROM openjdk:11.0.8-slim
VOLUME /tmp
COPY --from=MAVEN_BUILD /build/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]