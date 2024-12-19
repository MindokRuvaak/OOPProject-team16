package oopp.team16.model.gameLogic.Cards.Colors;

/**
 * Colors for a standard UNO deck.
 * The wild color is used for wild cards.
 *
 * The colors are implemented as singleton instances to
 * ensure that only one instance of each color exists.
 */
public class StdColors implements Color {
    private final String colorName;
    private static StdColors GREEN = new StdColors("green");
    private static StdColors BLUE = new StdColors("blue");
    private static StdColors RED = new StdColors("red");
    private static StdColors YELLOW = new StdColors("yellow");
    private static StdColors WILD = new StdColors("wild");


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

    public static StdColors wild(){
        return WILD;
    }

    @Override
    public String getColorName() {
        return colorName;
    }

    public String toString() {
        return colorName;
    }


}
