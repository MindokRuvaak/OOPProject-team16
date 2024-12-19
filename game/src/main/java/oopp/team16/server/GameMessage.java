package oopp.team16.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameMessage {
    private String type;
    private int sender;
    private Map<String, String[]> data;

    // Default constructor (required for deserialization)
    public GameMessage() {
        this.data = new HashMap<>();
    }

    // Constructor with type
    public GameMessage(String type) {
        this.type = type;
        this.data = new HashMap<>();
    }

    // Constructor with type and sender
    public GameMessage(String type, int sender) {
        this.type = type;
        this.sender = sender;
        this.data = new HashMap<>();
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public Map<String, String[]> getData() {
        return Collections.unmodifiableMap(data);
    }


    public void setData(Map<String, String[]> data) {
        this.data = data;
    }

    public void addData(String key, String[] value) {
        this.data.put(key, value);
    }
}
