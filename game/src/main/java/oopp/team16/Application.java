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
        // c.init(m); // might not be needed
        
        // m.AddListener(c); // does controller need to listen to model updates?
        // should controller be able to check if player action valid or allways send them through 
            // but model ''state'' makes invalid action have no effect?
        
        v = new ViewTerminal(m,c);
        // v.init(m,c);// might not be needed
        m.AddListener(v);
        
        m.initGame();
    }
}