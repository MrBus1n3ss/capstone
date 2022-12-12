FROM openjdk:11
COPY target/scheduling-*.jar scheduling.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "scheduling.jar"]