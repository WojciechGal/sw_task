# Star Wars Task

Spring Boot RESTful app created with various technologies.

## Built With

* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system
* 	[Prometheus](https://prometheus.io/) - Monitoring system and time series database
* 	[Lombok](https://projectlombok.org/) - Never write another getter or equals method again, with one annotation your class has a fully featured builder, Automate your logging variables, and much more.

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `pl.wojciech.sw_task.SwTaskApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin] like so:

```shell
gradle bootRun
```

## Running the application with docker (Windows 10 Home - Docker Toolbox)

First step is to build jar of the application:

```shell
gradlew build && java -jar build/libs/sw_task.0.0.1-SNAPSHOT.jar
```

Then build the docker container:

```shell
docker build --build-arg JAR_FILE=build/libs/*.jar -t wojciech/sw_task .
```

Finally run the fresh container:

```shell
docker run -p 8080:8080 wojciech/sw_task
```

App is running under 192.168.99.100 address