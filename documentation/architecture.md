# Diagrams

![diagram](https://user-images.githubusercontent.com/68298079/168492390-caa702d6-a5bc-401c-98a3-9b6d3117be03.png)
![image](https://user-images.githubusercontent.com/68298079/164043158-4e73ef30-fced-42b7-913b-8f72dc7657f5.png)

# Application Logic and Structure

The two most important classes of the application are `SnakeService` and `Game`. `SnakeService` is essentially responsible for all of the application logic and functionality. It also forms a layer between the classes that build the UI and the classes that manage the data of the application. `Game` forms the basis for the game logic and manages data related to the game, making the game easy to render.

`SnakeService` is responsible for e.g.:
* creation of new users
* retrieving information about the users (such as their scores, their snake color)
* modifying the users (renaming, changing the color)
* logging users in and off

`Game` is responsible for e.g.:
* tracking the snake's location
* managing the snake's direction of movement
* tracking the score
* detecting bumps

The main data classes of the application are `User` and `Score`. A user consists of an ID (Java's UUID), a name and a snake color. A score contains the associated user's ID and the score value. The `SnakeService` class uses these classes to communicate with the classes that manage the application's persistent data, namely implementations of the interfaces `UserDao` and `ScoreDao`.

The `UserDao` interface describes a class that functions as a data access object and handles persistent data relating to users. At the moment the only implementation of this interface is `CsvUserDao`, which saves its data in CSV files.

The `ScoreDao` interface, similarly to `UserDao`, describes a data access object class that handles persistent data relating to scores achieved by users. The only implementation for the moment being is `CsvScoreDao`, which saves its data in CSV files.

By using separate objects that handle persistent data, the `SnakeService` class does not need to know of the details relating to persistent data or how it's saved on the disk, instead, it can just retrieve the data of its interest by using the interfaces. The `SnakeService` class contains one instance of both interfaces, and the instances are injected to the class upon its construction by the main UI class, `SnakeUI`. This way it would be trivial to change the used implementation of the DAO interfaces in the future.

The user interface is built with JavaFX. The finished application will consists of 5 view as per the class diagram. To increase organisation and make the UI easier to scale and maintain, each view is its own class, extending `View`. To decrease references between classes and thereby make the code less spaghetti-like, the view classes have no reference to each other or the main UI class, `SnakeUI`. They're given a simple `ViewChanger` object, which is essentially a callback method that they can call to change the active view in the application.
