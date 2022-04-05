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
    * the uesr creation result is correct when an exception is thrown
