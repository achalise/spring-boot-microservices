# Developing mocroservices with SpringBoot

There is a trend towards microservices from monolith applications running
on a a single application server. The application now becomes a set of
loosely coupled modules which communicate with each other through 
APIs. The benefits are:

* Modularity
* Scalability by functional decomposition
* Fault isolation - failure in one service doesn't bring down the 
  entire application
* No long term commitment to a single technology stack
* Developer productivity - each service can be developed
  and deployed independently
* Easier to understand - a service does just one task

The challenges:

* 

SpringCloud provides abstractions and utility services
for developing and running microservices. This is a small
project to get my hands dirty and understand different tools
and services offered by SpringCloud to develop SpringBoot
microservices. The project is based on presentation by
Josh Long for Cloud Native Java in SpringOne (insert YouTube 
link here)


## Application

The application is just two services - user service to manage users
and activity service to manage activities for the users.

## Running MongoDB in Docker

We are going to use MongoDB for persistence. Although we could just install
and run a mongodb instance on a server, the idea is to be able to automate
installing and running a Mongo instance. We will dockerize
SpringBoot services as well and have all of our services running
on Docker.

`$ docker run -P -d --name mongo mongo`


See [Docker](https://www.docker.com/) for instructions on how to 
install Docker on Mac or Windows.


## Configuration management with Spring Cloud Config Server

Spring Cloud Config provides externalised configuration management
for distributed applications. The properties that are contained
in application.proerties are now served by config server. The 
config server uses a git repository to manage property files
for the different applications that depend on it for their properties.

Generate a config server template project by using http://start.spring.io
and selecting the config-server from Cloud Configuration options. 

config-server folder in this repo contains our config server.
Key point to note, the `application.properties` contains the following
entries:

`spring.cloud.config.server.git.uri=${HOME}/Desktop/config`
`management.security.enabled=false`
`server.port=8888`

The property `spring.cloud.config.server.git.uri` specifies the location
of the git repository containing application properties.

## Eureka Service Discovery

## Microservices

The actual services that we set out to write.

### User service

User service provides API end points for adding and updating users.
This is also generated as as SpringBoot application using 
http://start.spring.io and selecting the options
 
* `Web` - For REST endpoints
* `MongoDB` - SpringData for MongoDB
* `Config Client` - User externalised configuration from config server 
* `Eureka Discovery` - Service registration and discovery using Eureka
* `Actuator` - Out of box OPs console for SpringBoot app

The application contains `bootstrap.properties` to specify config server
details and the applicaton name:

`spring.application.name=user-service`
`spring.cloud.config.uri=http://localhost:8888`

The folder user-service in this repository contains code base for
user-service, to run simply `gradle bootRun`.

### Activity service


## Edge Server (Client proxy)


## Circuit breaker with Hystrix
## Loadbalancing
## CQRS with messaging


