FROM eclipse-temurin:17-jdk as builder

WORKDIR /spring

COPY gradle/ gradle/
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

RUN ./gradlew assemble

# base image
FROM gcr.io/distroless/java17-debian11

# port
EXPOSE 8080

# jar file
COPY --from=builder /spring/build/libs/secure-devex22-1.0.0.jar secure-devex22.jar

# entry
CMD ["secure-devex22.jar"]
