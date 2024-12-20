package oopp.team16;

import javafx.application.Application;
import javafx.stage.Stage;
import oopp.team16.view.StartView;

public class AppWindow extends Application {


    @Override
    public void start(Stage primaryStage) {
        StartView startView = new StartView(primaryStage);
        startView.show();
        // GameView gameView = new GameView(primaryStage, 3);
        // gameView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
