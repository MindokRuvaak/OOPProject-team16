package oopp.team16;

import javafx.application.Application;
import javafx.stage.Stage;
import oopp.team16.view.GameView;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameView gameView = new GameView(primaryStage);
        gameView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
