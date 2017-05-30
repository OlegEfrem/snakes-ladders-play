# VOA coding challenge
* Complete assignment can be found [here](https://github.com/OlegEfrem/snakes-ladders-play/blob/master/TASK.md);
* Only Feature 1 is implemented, as the idea is to showcase usage of the required technologies: Scala, Play, MongoDB;
* Given the Application Model and infrastructure is ready, implementing other features would be trivial;

# Requirements
* This is a scala play sbt project, and was developed and tested to run with: Java 1.8, Scala 2.11.11 and Sbt 0.13.15;
* MongoDB server cofigured via the environment variable: ```MONGO_URL``` defined [here](https://github.com/OlegEfrem/snakes-ladders-play/blob/master/conf/application.conf)

# How to run the application
To run application, call:
```
sbt run
```
To restart the application without reloading of sbt, use:
```
sbt re-start
```
# Highlights
## Libraries, Frameworks & Plugins
* Rest API based on [play-framework](https://www.playframework.com/);
* DB layer uses [MongoDB](https://www.mongodb.com/), [Reactive Mongo](http://reactivemongo.org/) as scala reactive db driver;
* Testing layer uses: [scala test](http://www.scalatest.org/) for defining test cases, [scala mock](http://scalamock.org/) for mocking dependencies in unit tests and 
[play-test](https://www.playframework.com/documentation/2.5.x/ScalaTestingWithScalaTest) for api tests;
* Plugins configured for the project are: [s-coverage](https://github.com/scoverage/sbt-scoverage) for code test coverage, [scala-style](http://www.scalastyle.org/) for code style checking and
[scala-iform](https://github.com/scala-ide/scalariform) for code formatting.

## Implementation details
* Code follows the following programming concepts: 
  * OOP (Object Oriented Programming) principles of abstraction (all operations (meaning function, methods and procedures) and parameters are abstracted concepts of the business domain language (to be read as DDD Domain Driven Design) 
  found in the [task description](https://github.com/OlegEfrem/snakes-ladders-play/blob/master/TASK.md), encapsulation (in the case classes, encapsulates factory operations and unique ID assignment).
  Inheritance and Polymorphism is shown on the DB level, allowing changing underlining db without affecting the service and api layers;
  * FP (Funtional Programming) principles of immutability (all data is immutable), higher order functions (mainly of the collection Api), heavy use of pattern matching, monadic constructions;
  * Declarative style where operations describe what is done and not how;
  * TDD with [ScalaTest](http://www.scalatest.org/) the test were created before the implementation;
  * Iterative Development that can be traced through git commit messages;
  * Intention Revealing Operations where all parts of the method contribute to the clarity: operation name, parameter names, parameter types, and operation return type;
  * Separation of concerns: each class/object/trait has clearly defined responsibilities that are fully testable and tested;
  * Meaningful commit and messages - commit per implemented feature, and on important code changes that might be usefull to revisit;
  * Uses [ScalaStyle](http://www.scalastyle.org/) plugin for code style checking and [scoverage](https://github.com/scoverage/sbt-scoverage) for code test coverage;
  
# Things TODO
* Given a different scale of the Application and longer development time several things would be done in different scenarios: 
  * Mocking (with a tool like [ScalaMock](http://scalamock.org/)) - service layer could be tested in isolation by having db layer mocked, for the given time frame given very thin functionality at this layer, it's good enough with being exercised from the BDD/integration layer;
  * BDD (with a tool like [Cucumber](https://cucumber.io/)) - having non technical audience as stackholders would make apropriate for a testing closer to the business level/language;
  * Performance Tests (with a tool like [Gatling](http://gatling.io/#/)) - having some SLA (Service Level Agreements) and thus non functional requirements would require to have this test layer in place;
  * CI/CD (Continuous Integration/Continuous Delivery with a tool like [Jenkins](https://jenkins.io/) or [TeamCity](https://www.jetbrains.com/teamcity/)) pipe line set up for easier delivery of the functionality;
  * Feature/Issue/Release/Time management and tracking with a tool like [Jira](https://www.atlassian.com/software/jira) or [YouTrack](https://www.jetbrains.com/youtrack/)
  * Cloud Infrastructure Provisioning and Configuration Management (on [AmazonCloudServices](https://aws.amazon.com/), [OpenStack](https://www.openstack.org/), 
  [Teraform](https://www.terraform.io/), [Puppet](https://puppet.com/), [Consul](https://www.consul.io/), etc)
  - given requirements for Elastic Systems (concept of the [Reactive Programming](http://www.reactivemanifesto.org/));
  
# API Behaviour
It's behaviour is defined in task description and tested by the API BDD style tests found [here](https://github.com/OlegEfrem/snakes-ladders-play/blob/master/test/bdd/SnakesLaddersTest.scala).
## The test output is: 
```aidl
[info] Feature: Feature 1 - Moving Your Token
[info]   Scenario: Token Can Move Across the Board
[info]     As a player 
[info]     I want to be able to move my token 
[info]     So that I can get closer to the goal 

[info]     Given the game is started 
[info]     When the token is placed on the board 
[info]     Then the token is on square 1 

[info]     Given the token is on square 1 
[info]     When the token is moved 3 spaces 
[info]     Then the token is on square 4 

[info]     Given the token is on square 1 
[info]     When the token is moved 3 spaces 
[info]     And then it is moved 4 spaces 
[info]     Then the token is on square 8 

[info]   Scenario: Moves Are Determined By Dice Rolls
[info]     As a player 
[info]     I want to move my token based on the roll of a die 
[info]     So that there is an element of chance in the game 

[info]     Given the game is started 
[info]     When the player rolls a die 
[info]     Then the result should be between 1-6 inclusive 

[info]     Given the player rolls a 4 
[info]     When they move their token 
[info]     Then the token should move 4 spaces 

[info]   Scenario: Player Can Win the Game
[info]     As a player 
[info]     I want to be able to win the game 
[info]     So that I can gloat to everyone around 

[info]     Given the token is on square 97 
[info]     When the token is moved 3 spaces 
[info]     Then the token is on square 100 
[info]     And the player has won the game 

[info]     Given the token is on square 97 
[info]     When the token is moved 4 spaces 
[info]     Then the token should move 4 spaces 
[info]     And the player has not won the game 

```
## Api Endpoints
* defined [here](https://github.com/OlegEfrem/snakes-ladders-play/blob/master/conf/routes)
* There are two groups of endpoints: 
  * CREATE/PLAY game endpoints: used for new player/game creation, as well as playing an existing game;
  * RETRIEVE endpoints: user to retrieve a saved game and forward it's data to PLAY game endpoints; 
  