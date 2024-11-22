package oopp.team16.model.gameLogic.Colors;

public class Blue implements Color{
    private static Blue instance;

    private Blue() {}

    public static Blue getColor() {
        if (instance == null) {
            instance = new Blue();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "Blue";
    }

    


}
