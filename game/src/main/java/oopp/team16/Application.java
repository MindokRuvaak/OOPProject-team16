package oopp.team16;

import oopp.team16.controller.Controller;
import oopp.team16.model.Model;
import oopp.team16.view.View;
import oopp.team16.view.ViewTerminal;

public class Application {

    private static Model m;
    private static View v;
    private static Controller c;


    // private Application(){
    //     m = new Model();
    //     m.init();
        
    //     c = new Controller(m);
    //     // c.init(m);
    //     m.AddListener(c);
        
    //     v = new View(m,c);
    //     // v.init(m,c);
    //     m.AddListener(v);
    // }


    private void init() {
        // initiate a game
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    public static void main(String[] args) {
        int n = 2; 
        m = new Model();
        
        c = new Controller(m);
        // c.init(m); // might not be needed
        
        m.AddListener(c); // does controller need to listen to model updates?
        // should controller be able to check if player action valid or allways send them through 
            // but model ''state'' makes invalid action have no effect?
        
        v = new ViewTerminal(m,c);
        // v.init(m,c);// might not be needed
        m.AddListener(v);
        
        m.initGame();
    }
}