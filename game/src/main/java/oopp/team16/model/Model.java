package oopp.team16.model;

import java.util.ArrayList;
import java.util.List;

import oopp.team16.Listener;

public class Model {
    List<Listener> listeners = new ArrayList<>();




    public void AddListener(Listener l){
        listeners.add(l);
    }
}