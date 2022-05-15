### Week 3

* The UI for creating a new user has been created (visual changes incoming), but the logic behind the process is unfinished
* Created classes (most of them unfinished):
  * Main - runs the application
  * SnakeUi - forms the basis and handles the logic of the app's UI
  * View - an abstract class that describes an application view
  * LoginView - extends View, creates the login view
  * SnakeService - responseible for the application logic
  * User - describes a user in the game
  * UserCreationResult - the outcome of a user creation process
  * UserDao - an interface that describes an object that handles a user data source
  * CsvUserDao - an implementation of UserDao, handles a CSV file as the user data source
* Tests:
  * SnakeService:
    * a single user can be created
    * cannot create a user with too short a name
    * cannot create a user with a name that's already taken
    * the user creation result is correct when an exception is thrown

### Week 4

* The functionality of the UI for logging in users and creating new users has been finished, visual changes may be incoming
* The logic behind creating new users and logging in users is functional
* New classes:
  * MenuView - extends View, creates the menu view (unifinished)
  * ViewChanger - a helper interface, makes it possible for View instances to change the view without a circular dependency
  * ViewType - a helper enum, used with ViewChanger to tell the SnakeUi class what view we want to change to
* New tests:
  * SnakeService
    * can login to a user that has been created
    * after logging in, the currently logged in user changes
  * CsvUserDao:
    * after adding a user, it appears on the list of all users returned by the class
    * after adding a user, it can be found by name

### Week 5
* The menu view is largely finished, some buttons still unfunctional
* Created the profile view
* Added the logic for renaming users and setting user colors
  * Added unique identifiers (UUIDs) for users, this makes it possible to refer to the same user even with a changed name (file format: uuid;name;color)
* New classes:
  * ProfileView - extends View, creates the profile view
  * UserOperationResult - renamed from UserCreationResult
* New tests:
  * SnakeService
    * can rename a user
    * can set a user's color

### Week 6
* The menu view is finished (apart from the *Start a new game* button, since the game view has not been created yet)
* Created the high score view
* Added logic for registering scores and managing score data persistently
* New classes:
  * ScoreDao - an interface that describes an object that handles a score data source
  * CsvScoreDao - an implementation of ScoreDao, manages its data in a CSV file
  * Score - describes a score, consists of a value and a user's UUID
  * HiScoreView - extends View, creates the high score view
* New tests:
  * CsvScoreDao
    * after adding a score, it appears on the list
    * after adding a score, it can be found by the user that set the score
    * scores are returned in descending order
  * SnakeService
    * an updated list of a user's score can be retrieved
    * an udpated list of all scores can be retrieved
    * N top scores can be found

### Week 7
* The game view is finished. Game loop and logic implemented.
  * The snake can be controlled using the arrow keys.
  * The snake grows by eating food.
  * The game is over if the snake hits its body with its head.
* New classes:
  * GameView - extends View, creates the game view (renders the game)
  * Game - responsible for the game logic
* New tests:
  * Game
    * after advancing a step in the game, the head moves to the correct direction
    * the direction can be changed
    * the score grows after eating
    * direction does not change unless the specified direction is valid
    * cannot turn 180 degrees
    * game is over if the snake bumps its head to its body
