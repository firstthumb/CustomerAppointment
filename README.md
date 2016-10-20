TransactionRest
=================

[![Build Status](https://travis-ci.org/firstthumb/CustomerAppointment.svg?branch=master)](https://travis-ci.org/firstthumb/CustomerAppointment)

### Dependency
* [Spring Boot](http://projects.spring.io/spring-boot/)
* [Immutables](http://immutables.github.io)
* [Lombok](https://projectlombok.org/)
* [Hamcrest](http://hamcrest.org/)

### Installation
You need to install [maven](https://maven.apache.org/) for managing dependecies

```
mvn clean install
java -jar target/ca-rest-1.0-SNAPSHOT.jar
```
### Rest API
```
GET /api/v1/customers/{customerId}/appointments/next
Response { "appointment_id": 1, "date": "" }

POST /api/v1/customers/{customerId}/appointments/last/rate/{rating (TERRIBLE, POOR, AVERAGE, GOOD, EXCELLENT)}
Response { "appointment_id": 1, "date": "" }

POST /api/v1/customers
Payload { "first_name": "", "last_name": "" }
Response { "customer_id": 1, "first_name": "", "last_name": "", "appointments" : [ { "appointment_id": 1, "date": "" } ] }

POST /api/v1/audiologists/{audiologistId}/appointments
Payload { "customer_id": 1, "date": "" }
Response { "appointment_id": 1, "date": "" }

POST /api/v1/audiologists/{audiologistId}/appointments
Payload { "customer_id": 1, "date": "" }
Response { "appointment_id": 1, "date": "" }

GET /api/v1/audiologists/{audiologistId}/appointments/overview
Response 

GET /api/v1/audiologists/{audiologistId}/appointments?date=NextWeek
Response 

```
