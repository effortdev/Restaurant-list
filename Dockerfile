# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# gradlew에 실행 권한 부여 (리눅스 환경 대비)
RUN chmod +x ./gradlew
# JAR 파일 생성
RUN ./gradlew build -x test --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]