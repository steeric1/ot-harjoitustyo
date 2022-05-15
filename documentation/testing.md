# Test Document

This project uses JUnit to perform unit tests and some integration tests. It has also extensively been tested manually.

## Unit and Integration Tests

### Application Logic

The `SnakeService` class is responsible for most of the application logic (apart from the game loop). For instance, the following things are tested:
* users can be created
* it is possible to log into users
* cannot create a user with a name that is taken
* users can successfully be renamed
* user colors can be set

The `SnakeService` class closely integrates with the DAO classes (`UserDao` and `ScoreDao`). This integration is tested using the fake DAO classes `FakeUserDao` and `FakeScoreDao`.

### DAO classes

Also the DAO classes themselves are tested. The tests relate to the correctness of the data storage, the following tests, for instance, are tested:
* after adding a user, it appears on the list of users
* after adding a user, it can be found
* scores are retrieved in descending order

### Game Logic

The `Game` class is also tested to make sure the game logic is correct. The following things, for example, are tested:
* direction can be changed
* the score grows after eating food
* the game is over after the snake bumps its head to its body

## Test Coverage

![image](https://user-images.githubusercontent.com/68298079/168492925-cd7dce26-30a4-4634-982b-60a685158218.png)

## Manual Testing

All parts of the application have been thoroughly tested manually. No apparent bugs have been found. The features have been tested according to the Requirements Specification. The project has also been tested on Windows and Linux, and the project seems to compile and function properly on both platforms.
