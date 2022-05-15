# Requirements Specification – Snake

## The Purpose of the Application

The application will implement a simple 2-dimensional game known as 'Snake', where the user has the objective to control a snake and direct it to eat food, thereby growing the snake. The goal is to grow the snake as large as possible without letting the snake bump its head to its body. The application will make it possible to create multiple users, each having their own high score. The users can also see the top-10 scores achieved locally. Additionally, the users can choose their own snake color and rename their users.

## Functionality

There will be five views in the application, each of which have their own functionality. The views are the login view, the menu view, the profile view, the high score view and the game view.

### Login View - Completed

* The user will be able to choose one of the users listed - Completed
    * No passwords, the application is meant to be used locally within safe environments
* The user can create a new user - Completed
    * The username will need to be unique and the length at least 2 characters

### Menu View

* After having logged in, the user is presented with this view
* The user can:
    * Open profile settings (opening the profile view) - Completed
    * See high scores (opening the high score view) - Completed
    * Start a new game (opening the game view) - Completed
    * Log off (opening the login view) - Completed
* Additionally, the user's personal high score is shown in this view - Completed

### Profile view - Completed

* The user can edit their nickname - Completed
* The user can set the color of their snake - Completed

### High Score View - Completed

* The user can see the 10 best scores achieved locally - Completed
    * If fewer than 10 scores are found, then those are shown - Completed
* The number of listed high scores per user is not limited - Completed

### Game View - Completed

* This is the view that the user will spend most of their time in
* The user can see the snake inside a bordered square and move it left, right, up and down using the arrow keys - Completed
* The user can see their current score and their current best score - Completed
* When the user loses, a game over view is shown - Completed
    * The user can see
        * the score they achieved, - Completed
        * whether they made it on the top-10 list - Completed
        * whether they set a personal best - Completed
        * their (previous) high score - Completed
        * a prompt to start a new game or to quit to menu screen - Completed
