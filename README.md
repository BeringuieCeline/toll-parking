# toll-parking

A microservice to manage a Toll Parking using [Spring Boot](https://spring.io/projects/spring-boot)

##  Getting Started

All Maven plugins and dependencies are available from [Maven Central](https://search.maven.org/). Please have installed
* JDK 1.8 (tested with both Oracle and OpenJDK)
* Maven 3.3+ (also tested with 3.5.x)
* Docker (Tested with the 19.03.5)

## Used Library and plugin

* [Spring Boot](https://spring.io/projects/spring-boot)
* [Jpa](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference)
* [h2](https://www.h2database.com/html/main.html)
* [Swagger2](https://swagger.io/)
* [Lombok](http://projectlombok.org/)
* [JUnit 5](https://junit.org/junit5/)
* [Jacoco](https://www.jacoco.org/jacoco/)

## Building

```
mvn clean install
```

### Build Docker container
#### Linux

```
linux-dockerize.sh
```
#### Windows


```
windows-dockerize.bat
```
## Running

### Java Style

Just run the generated Jar as usually

```
java target/*.jar
```

### Docker Style

Run the image built by docker using :

```
docker run -p 9090:9090 -t microservice
```

## API Documentations

Access to the Swagger2 UI through : http://localhost:9090/swagger-ui.html

## JavaDoc

Access to the JavaDoc by openning the file : `./javadoc/index.html`

## Jacoco

Access to the Jacoco report by openning the file : `./target/site/jacoco/index.html`

## How to use

Once it's build and started you can access to the Swagger UI through :  http://localhost:9090/swagger-ui.html
You can call the service using [Postman](https://www.getpostman.com/) or [Curl](https://curl.haxx.se/). 
The call order is the following one :
* `GET : /v1/carSlots/{type}` to retrieve the list of the available slots of type `type`. `type`can be : `"Gasoline"`, `"Low Powered electrical"` or `"High Powered electrical"`
* `POST : /v1/car/{id}/{type}`  to park your car in slot `id`, `type` refer to the type of your car and can be : `"Gasoline"`, `"Low Powered electrical"` or `"High Powered electrical"`. Service return an unique ID to identify your car 
* `GET : /v1/bill/{uuid}` to get billed, the bill will be valid for 5 minutes, if you don't leave the parking in this time, you will have to re-call the `bill` service. `uuid` refer to the unique id of your car.
* `DELETE : /v1/car/{uuid}` leave the parking

## Links

* https://github.com/Djoe-Denne/toll-parking

## Copyright &copy; 2019 [Djoé DENNE](https://github.com/Djoe-Denne)

> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>     http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.