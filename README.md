# Uno!
This is the project of team 16 for the course Object Orientated Programming Project - DIT213

We are making uno :)
## Peer review
The main branch *should* always be in a runnable state with the latest finnished and implemented features. 
We have a user guide document with description and an overview of how to play and run the code. For more information on the project as a whole please see the RAD.

We have used Maven to create this project meaning as long as your IDE recognices it as a maven project all the dependencies (e.g.javaFX) should be handeled automatically. If you are using IntelliJ and issues with maven occur this stackocerflow thread may be of help: 

https://stackoverflow.com/questions/15046764/intellij-idea-not-recognizing-classes-specified-in-maven-dependencies

If you recieve the following error message; 
*JavaFX runtime components are missing, and are required to run this application*
that means the project cannot find your JavaFX installation. To solve this if you use intellij please see: 

https://stackoverflow.com/questions/51478675/error-javafx-runtime-components-are-missing-and-are-required-to-run-this-appli 

If you are using vscode create a launch.json and add 

"vmArgs": "--module-path \"path/to/your/javafx/installation/lib\" --add-modules javafx.controls,javafx.fxml"

to the configuration for the AppWindow launcher. 


If you (a peer-reviewer) have issues running the code or encounter a bug please do create a new issue here on git <3

### known issues
it is possible to start a server and connect multiple people to it. Once more than two clients have connected to the server it is possible to start a game, this is done when every connected player has pressed start. Pressing start will send the client to the game board but the game won't start until all players have entered the game board. 
The players are able to receive the relevant game info from the server but there will occur NullPointerExceptions which is due to javafx issues

the GUI view has had a lot of it's ability to play stripped from it in preparation for the multiplayer implementation and thus it is not possible to play the game with this.
