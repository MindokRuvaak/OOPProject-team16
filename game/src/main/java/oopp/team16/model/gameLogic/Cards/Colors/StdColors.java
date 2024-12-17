package oopp.team16.model.gameLogic.Cards.Colors;
public class StdColors implements Color {
    private final String colorName;
    private static Color GREEN = new StdColors("green");
    private static Color BLUE = new StdColors("blue");
    private static Color RED = new StdColors("red");
    private static Color YELLOW = new StdColors("yellow");

    //make a nocolor color? or black
    // maybe ''wild'' as a colour?

    private StdColors(String colorName){
        this.colorName = colorName;
    }

    public static Color green() {
        return GREEN;
    }

    public static Color blue() {
        return BLUE;
    }

    public static Color red() {
        return RED;
    }

    public static Color yellow() {
        return YELLOW;
    }

    @Override
    public String getColorName() {
        return colorName;
    }

    public String toString() {
        return colorName;
    }


}
