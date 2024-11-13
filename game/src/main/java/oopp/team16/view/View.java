package oopp.team16.view;

import oopp.team16.model.ModelListener;
import oopp.team16.controller.Controller;
import oopp.team16.model.Model;

public class View implements ModelListener {

    Model m;
    Controller c;


    public View(Model m, Controller c) {
        this.m = m;
        this.c = c;
    }





    public void init(Model m, Controller c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}