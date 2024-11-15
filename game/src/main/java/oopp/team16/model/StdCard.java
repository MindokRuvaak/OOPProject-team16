package oopp.team16.model;

public class StdCard implements Card{

    private final Color color;
    private final Value value;

    public StdCard(Color color, Value value){
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
