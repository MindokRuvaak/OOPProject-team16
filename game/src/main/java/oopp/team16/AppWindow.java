package oopp.team16;

import javafx.application.Application;
import javafx.stage.Stage;
import oopp.team16.view.GameView;
import oopp.team16.view.LobbyView;

public class AppWindow extends Application {


    @Override
    public void start(Stage primaryStage) {
        LobbyView lobbyView = new LobbyView(primaryStage);
        lobbyView.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
