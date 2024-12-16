package main.java.oopp.team16;

import main.java.oopp.team16.controller.ControllerTerminal;
import main.java.oopp.team16.model.Model;
import main.java.oopp.team16.view.View;
import main.java.oopp.team16.view.ViewTerminal;

public class AppTerminal {

    private static Model m;
    private static View v;
    private static ControllerTerminal c;

    public static void main(String[] args) {
        m = new Model();

        c = new ControllerTerminal(m);

        v = new ViewTerminal(m, c);
        m.addListener(v);

        m.initGame();
        m.startGame();
    }
}
