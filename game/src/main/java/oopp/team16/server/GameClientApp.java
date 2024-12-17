package oopp.team16.server;

import oopp.team16.controller.GameViewController;

public class GameClientApp {
    public static void main(String[] args) {
        GameClient client = new GameClient("localhost", 12345);
        GameClientController controller = new GameClientController(client);

        // Link the controller with the view
        GameViewController viewController = new GameViewController();
        viewController.setClientController(controller);

        // Start the JavaFX UI
        launchGameUI(viewController);
    }

    private static void launchGameUI(GameViewController controller) {
        // Code to initialize and display the JavaFX view
    }
}
