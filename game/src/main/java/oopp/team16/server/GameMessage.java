package oopp.team16.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameMessage {
    private String type;
    private int sender;
    private Map<String, String[]> data;

    public GameMessage(String type) {
        this.type = type;
        this.data = new HashMap<>();
    }

    public GameMessage(String type, int sender) {
        this.type = type;
        this.sender = sender;
        this.data = new HashMap<>();
    }

    @Override
    public String toString() {
        return getType();
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public int getSender() {
        return sender;
    }

    public Map<String, String[]> getData() {
        return Collections.unmodifiableMap(data);
    }

    public void addData(String key, String[] value) {
        this.data.put(key, value);
    }

    // set metoder används ej hittills.
    public void setType(String type) {
        this.type = type;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public void setData(Map<String, String[]> data) {
        this.data = data;
    }


}
