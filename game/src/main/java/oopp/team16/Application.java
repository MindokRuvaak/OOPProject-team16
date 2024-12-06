package oopp.team16;

import oopp.team16.controller.Controller;
import oopp.team16.model.Model;
import oopp.team16.view.View;
import oopp.team16.view.ViewTerminal;

public class Application {

    private static Model m;
    private static View v;
    private static Controller c;

    
    public static void main(String[] args) {
        m = new Model();
        
        c = new Controller(m);
        
        v = new ViewTerminal(c);
        m.addListener(v);
        
        m.initGame();
        m.startGame();
    }
}