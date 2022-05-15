# Manual

## Installation

Download the [snake.jar](https://github.com/steeric1/ot-harjoitustyo/releases/download/loppupalautus/snake.jar) file from the releases.

**Note:** The program saves its data in two files, `users.csv` and `scores.csv`. The files will be created in the working directory upon runtime.

## Running

The program can be run by issuing the following command:
<pre>
java -jar <<i>path-to.jar</i>>
</pre>
If your current directory is the same directory where you downloaded the jar file, you can simply run
```
java -jar snake.jar
```

## Usage

When running the application, the first view the user sees is the login view:
![image](https://user-images.githubusercontent.com/68298079/166478475-3f33296c-5b64-44c8-8cbd-5114cb14f052.png)  
To create your first user, click *Create new user*. After that, a prompt to enter a new username will appear. Enter a username and click *Create user*. Clicking the button will log you in and take you directly to the menu view. If pre-existing users were found, they will be listed in the login view as follows:  

![image](https://user-images.githubusercontent.com/68298079/166478896-f3b99210-4f7b-4321-a374-f2d162673370.png)  

Log in a user by clicking the user's name.

After having logged in a user, you will see the menu view:  

![image](https://user-images.githubusercontent.com/68298079/166479189-516f913f-e56d-40cc-9b31-c122078baed3.png)  

In the menu view, you can see your personal high score (if one has been set). You can also open your profile settings, view high scores, start a new game  or log off to return to the login view. By clicking *Open profile settings*, you will be presented with the profile view:  

![image](https://user-images.githubusercontent.com/68298079/166479439-24b52620-4c8f-4028-bb6a-8e32486bfac5.png)  

In the profile view, you can change your username or your snake color. You can also return back to the menu view.

By clicking *View high scores* in the menu view, you will see a list of the max. 10 best locally achieved high scores, or a replacement text if none were found:  

![image](https://user-images.githubusercontent.com/68298079/166479743-cf691754-7040-4dd0-9a31-8ef6f60109e8.png)  

You can return back to the menu by clicking *Back to menu*.

By clicking *Start a new game* in the menu view, a new game of Snake will start. You can guide your snake with the arrow keys. Eat green food to grow the snake and reach a higher score. Make sure not to bump the snake's head to its body. GLHF!  

![image](https://user-images.githubusercontent.com/68298079/168492043-f9ec8503-6586-408e-b980-683e161c4a5e.png)  

If you manage to grow your snake uncontrollably long and do try to eat off a chunk of the snake's body, the game over view is shown. This view will tell you all kinds of interesting information, such as the score you reached, your personal best score (and whether you got a new one) and where you placed on the ranking. It also prompts you to start a new game or if you so wish, quit to main menu.  

![image](https://user-images.githubusercontent.com/68298079/168492093-81c4330b-6f05-469c-a6cc-0cc611b1656f.png)  
