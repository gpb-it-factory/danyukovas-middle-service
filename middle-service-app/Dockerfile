FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY gradle/ gradle
COPY  gradlew.bat ./
COPY  build.gradle ./
COPY  settings.gradle ./
COPY  gradlew ./

COPY src ./src

CMD ["./gradlew", "bootRun"]