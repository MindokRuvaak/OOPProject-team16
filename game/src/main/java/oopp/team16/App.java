package oopp.team16;

import oopp.team16.controller.UnoController;
import oopp.team16.view.StartView;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }
    static UnoController unoCC;

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        StartView sv = new StartView(unoCC);
    }
    
    
}
