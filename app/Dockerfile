FROM java:8

ENTRYPOINT ["/usr/bin/java", "-jar", "-Dspring.profiles.active=docker", "/usr/share/app/app.jar"]

EXPOSE 8080

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/app/app.jar
ADD src/main/resources/application-docker.yml /usr/share/app/config/application.yml