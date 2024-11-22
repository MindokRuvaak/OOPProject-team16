package oopp.team16.model.gameLogic.Colors;

public class Yellow implements Color {

    private static Yellow instance;

    private Yellow() {}

    public static Yellow getColor() {
        if (instance == null) {
            instance = new Yellow();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "Yellow";
    }

}
