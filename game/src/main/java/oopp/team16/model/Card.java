package oopp.team16.model;

public class Card {

    private final Color color;
    private final Value value;

    public Card(Color color, Value value){
        this.color = color;
        this.value = value;
    }

    public Color getColor(){
        return this.color;
    }

    public Value getValue(){
        return this.value;
    }


}
