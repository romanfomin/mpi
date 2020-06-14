FROM maven:3-openjdk-8 as builder
WORKDIR /app
COPY . .
ENV MAVEN_OPTS="-Dmaven.repo.local=/app/.m2"
RUN mvn package

FROM openjdk:8-jdk-alpine
COPY --from=builder /app/target/mpi-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
