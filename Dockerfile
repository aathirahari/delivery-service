FROM openjdk:8

COPY ./target/deliveryservice-*.jar deliveryservice.jar

EXPOSE 8082

CMD ["java","-jar","-Dspring.profile.active=dev","deliveryservice.jar"]