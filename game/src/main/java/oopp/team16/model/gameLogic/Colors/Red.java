package oopp.team16.model.gameLogic.Colors;

public class Red implements Color{
    private static Red instance;

    private Red() {}

    public static Red getColor() {
        if (instance == null) {
            instance = new Red();
        }
        return instance;
    }

}

