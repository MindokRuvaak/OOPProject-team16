package oopp.team16.Utility;

// GameMessageParser.java
import com.google.gson.Gson;
import oopp.team16.server.GameMessage;

public class GameMessageParser {
    private static final Gson gson = new Gson();

    public static String serialize(GameMessage message) {
        return gson.toJson(message);
    }

    public static GameMessage deserialize(String json) {
        return gson.fromJson(json, GameMessage.class);
    }
}
