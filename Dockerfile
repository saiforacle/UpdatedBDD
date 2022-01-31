#base image of JDK(First Layer)
FROM openjdk

#Second layer in which the making the directory to run in.

RUN mkdir /app


COPY . /app


WORKDIR /app/src/test/java/Step


RUN javac TestRunner.java


# base image for Maven Repositories

From maven

COPY . /app

WORKDIR /app


CMD mvn test -Dcucumber.filter.tags="@smoke and @Creation1 and @ENT_CONF_CRUD_API_1"