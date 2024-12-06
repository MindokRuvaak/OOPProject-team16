package oopp.team16.model.gameLogic.Cards.Colors;
public class StdColors implements Color {
    private final String colorName;
    private static StdColors GREEN = new StdColors("green");
    private static StdColors BLUE = new StdColors("blue");
    private static StdColors RED = new StdColors("red");
    private static StdColors YELLOW = new StdColors("yellow");

    //make a nocolor color? or black

    private StdColors(String colorName){
        this.colorName = colorName;
    }

    public static StdColors green() {
        return GREEN;
    }

    public static StdColors blue() {
        return BLUE;
    }

    public static StdColors red() {
        return RED;
    }

    public static StdColors yellow() {
        return YELLOW;
    }

    public String getColorName() {
        return colorName;
    }

    public String toString() {
        return colorName;
    }


}
