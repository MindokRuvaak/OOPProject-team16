package oopp.team16;

import javafx.application.Application;
import javafx.stage.Stage;
import oopp.team16.controller.MainMenuController;
import oopp.team16.view.StartView;

public class AppWindow extends Application {


    @Override
    public void start(Stage primaryStage) {
        MainMenuController mmc = new MainMenuController();
        StartView startView = new StartView(primaryStage, mmc);
        startView.show();
    }

    public static void main(String[] args) {
        launch(args);
    } // localhost
}
