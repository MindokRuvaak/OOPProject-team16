package oopp.team16.model.gameLogic.Colors;

public class Green implements Color {

    private static Green instance;

    private Green() {}

    public static Green getColor() {
        if (instance == null) {
            instance = new Green();
        }
        return instance;
    }

}
