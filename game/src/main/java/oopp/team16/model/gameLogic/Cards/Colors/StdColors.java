package oopp.team16.model.gameLogic.Cards.Colors;
public class StdColors implements Color {
    private final String colorName;
    private static StdColors GREEN = new StdColors("green");
    private static StdColors BLUE = new StdColors("blue");
    private static StdColors RED = new StdColors("red");
    private static StdColors YELLOW = new StdColors("yellow");
    private static StdColors WILD = new StdColors("wild"); //for wildCards


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

    public static Color wild(){
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
