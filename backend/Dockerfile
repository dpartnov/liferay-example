##
## Stage 1 - Build using Maven
##
FROM maven:3-openjdk-11 as BUILD

# Copy entire project and build it
# This layer is built when something in the project changes
COPY . /project/
WORKDIR /project/
RUN mvn clean install


##
## Stage 2 - Create runnable image
##
FROM openjdk:11.0-jre

# REST API
EXPOSE 8081

RUN mkdir /backend/

COPY --from=BUILD /project/target/*.jar /backend/

WORKDIR /backend/
ENTRYPOINT ["java","-jar","calculator-rest-0.0.1-SNAPSHOT.jar"]
