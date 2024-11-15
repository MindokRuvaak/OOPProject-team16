package oopp.team16.model.gameLogic;

public class StdCard implements Card{

    private final Color color;
    private final Value value;

    public StdCard(Color color, Value value){
        this.color = color;
        this.value = value;
    }

    @Override
    public Color getColor(){
        return this.color;
    }

    @Override
    public Value getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        return color.toString() + " " + value.toString();
    }

}
