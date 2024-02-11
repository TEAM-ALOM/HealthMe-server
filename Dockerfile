# Dockerfile

# jdk17 Image Start
FROM openjdk:17

# 인자 설정 - JAR_File

ARG DB_HOST \
    DB_PORT \
    DB_NAME \
    DB_USER_NAME \
    DB_PASSWORD \
    MAIL_HOST \
    MAIN_PORT \
    MAIL_USER_NAME \
    MAIL_PASSWORD \
    JWT_SECRET \
    WEB_HOOK_URL

ENV DB_HOST=${DB_HOST} \
    DB_PORT=${DB_PORT} \
    DB_NAME=${DB_NAME} \
    DB_USER_NAME=${DB_USER_NAME} \
    DB_PASSWORD=${DB_PASSWORD} \
    MAIL_HOST=${MAIL_HOST} \
    MAIN_PORT=${MAIL_PORT} \
    MAIL_USER_NAME=${MAIL_USER_NAME} \
    MAIL_PASSWORD=${MAIL_PASSWORD} \
    JWT_SECRET=${JWT_SECRET} \
    WEB_HOOK_URL=${WEB_HOOK_URL}

ARG JAR_FILE=build/libs/*.jar \
# jar 파일 복제
COPY ${JAR_FILE} app.jar

# 인자 설정 부분과 jar 파일 복제 부분 합쳐서 진행해도 무방
#COPY build/libs/*.jar app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "-Dlogback.debug=true",  "app.jar"]
