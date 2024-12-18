package oopp.team16;

import oopp.team16.controller.ControllerTerminal;
import oopp.team16.model.Model;
import oopp.team16.view.View;
import oopp.team16.view.ViewTerminal;

public class AppTerminal {

    private static Model m;
    private static ViewTerminal v;
    private static ControllerTerminal c;

    public static void main(String[] args) {
        m = new Model();
        v = new ViewTerminal(m);
        
        c = new ControllerTerminal(m, v);
        
        m.addGameListener(c);
        m.addListener(c);
        
        m.initGame();
        m.startGame();
    }
}
