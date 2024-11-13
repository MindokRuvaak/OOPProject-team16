package oopp.team16;

import oopp.team16.controller.Controller;
import oopp.team16.model.Model;
import oopp.team16.view.View;

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
        m.init();
        
        c = new Controller(m);
        // c.init(m);
        m.AddListener(c);
        
        v = new View(m,c);
        // v.init(m,c);
        m.AddListener(v);
        // Application a = new Application();
        // a.init();
        // a.runGame();
    }    
}