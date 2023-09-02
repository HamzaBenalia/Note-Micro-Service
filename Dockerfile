FROM openjdk:17-alpine
ENV PORT=9090
COPY target/*.jar ./app-note.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app-note.jar"]
