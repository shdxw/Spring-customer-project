FROM adoptopenjdk:15-jre-hotspot
ARG JAR_FILE=*.jar
COPY target/${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]