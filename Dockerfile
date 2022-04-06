FROM openjdk:16
ADD target/usersCRUD-0.0.1-SNAPSHOT.jar usersCRUD.jar
ENTRYPOINT ["java", "-jar", "usersCRUD.jar"]
