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
