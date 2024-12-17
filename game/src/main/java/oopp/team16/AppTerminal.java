package oopp.team16;

import oopp.team16.controller.ControllerTerminal;
import oopp.team16.model.Model;
import oopp.team16.view.View;
import oopp.team16.view.ViewTerminal;

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
