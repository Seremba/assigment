# Assignment Sebastian Aburto

### Time
- Starting Thursday Jul 29 ~22:00
- End Sunday Aug 01 ~15:30

Total time approximate: about ~8 hours with many interruptions between the Jul 29 to Aug 1.

## Assumptions

- An aquarium could have multiple species or none
- A Specie must have only one aquarium
- Specie names are unique
- Liters is the unit of size aquariums, but the api returns liters and gallons
- No security implementation
- No paginate results
- No endpoint to create aquarium
- Aquarium glass type and shape are enumeration values, just for this assignment only usage of "STRONG" as glass type and "RECTANGLE" as shape.
- No observability features like logs, counters, metrics.


# Requirements

### Model
There's only two concepts involved in this aquatic world:

- [x] Aquariums: they can vary in glass type, size (litres), and shape.
- [x] Fish: they can vary in species, color, and number of fins. Think of a few but at least have a goldfish and a guppy.

There are also some rules:

- [x] Goldfish can't go in the same aquarium as guppies.
- [x] Fish with three fins or more don't go in aquariums of 75 litres or less.


### API endpoints
Now, Pet Fish Co. wants to have an application to keep track of all of its aquariums and their fish by having access to two endpoints:
- [x] one to create or update a fish, and
- [x] one to retrieve all fish for a given aquarium.


## Bonus
- [x] If you want to go out of your way and flex those dev skills, we like you to take into account that Pet Fish Co. is considering opening up a store across the pond in the US. That means they’ll have to be able to convert their aquarium size from litres to gallons.


## What do we expect from you
- [x] Code that is fully working (bugs are perfectly fine, but your solution be functional and not just theoretical).
- [x] A **Docker Compose** file that lets us run a test environment for your solution.
- [x] A written `README.md` with instructions on how to run your test environment, as well as an explanation on how/why did you design things the way you did.



# Technologies

- Java 11
- Gradle
- Spring Boot (web, data-jdbc)
- Junit 5, Mockito
- Flywaydb
- Mysql
- H2

## Decisions

- **Java 11**: because I have more experience than Java 12-16. Java 10 or lower I haven't used since 2019.
- **Spring boot**, it has everything necessary to make a http-rest back-end service with default conventions, like usage of json.
- **Spring Data JDBC**, it is a lightweight data access framework based on spring-data and jdbc, it gives fully control of the query, commands and mapper between the application and the database.
- **lombok**, it makes easier to create builders and value object.
- **Domain-driven design** approach, usage of Aggregate Root, value object, domain model/services and repositories.
- **Hexagonal Architecture** (aka or similar as: onion, ports and adapters, clean), it plays very nice with DDD, application services implements the use case using the domain model and services as a facade, separation of layer between domain and external system, anti-corruption layer between not conformist dependencies.
- **Junit 5 and Mockito**, for TDD I usage of mocks for outside-in approach. For test after implementation (component test) I usage spring test framework.
- **H2 for component tests**, usage of memory database like h2 makes easier and faster the execution of component test, but not always h2 support the same syntax and function as MySQL, that is the reason I had to use some bypass functions.

# Testing strategy

- Unit test, using TDD approach and using mocks.
- Acceptance Tests, using component tests that encapsulate the behavior required. Usage of h2 memory database


# How to run it

## Requirements:

- docker-compose
- Jdk 11
- Make

## Run

```sh
make run

```

or manually:

```sh
./gradlew clean bootJar
docker-compose down
docker-compose build
docker-compose up -d
```

## Usage

- Using curl

### Get all aquariums:


```sh
curl http://localhost:8080/aquariums
```

#### Example result:

```json
[
  {
    "id": "Aquarium1",
    "glassType": "STRONG",
    "size": {
      "liters": 199,
      "gallons": 52.570228
    },
    "shape": "RECTANGLE"
  },
  {
    "id": "Aquarium2",
    "glassType": "STRONG",
    "size": {
      "liters": 300,
      "gallons": 79.25160000000001
    },
    "shape": "RECTANGLE"
  },
  {
    "id": "Aquarium3",
    "glassType": "STRONG",
    "size": {
      "liters": 30,
      "gallons": 7.925160000000001
    },
    "shape": "RECTANGLE"
  }
]
```

### Insert new fish

```sh
curl http://localhost:8080/fish -X POST --data '{"color": "RED", "specie": "salmon", "aquariumID": "Aquarium2", "fins": 2, "stock": 10}' -H "Content-Type: application/json"

```


### Get fishes in a given aquarium id:

```sh
curl http://localhost:8080/aquariums/Aquarium2/fishes

```

Example:
```json
[
  {
    "specie": "guppy",
    "fins": 2,
    "color": "RED",
    "stock": 60,
    "noCompatibleSpecies": [],
    "aquariumId": "Aquarium2",
    "big": false
  },
  {
    "specie": "salmon",
    "fins": 2,
    "color": "RED",
    "stock": 10,
    "noCompatibleSpecies": [],
    "aquariumId": "Aquarium2",
    "big": false
  }
]

```

### Update Fish

```sh
curl http://localhost:8080/fish -X PUT --data '{"specie": "salmon", "stock": 99}' -H "Content-Type: application/json"

```

### Stop the application

```sh
make stop
```
