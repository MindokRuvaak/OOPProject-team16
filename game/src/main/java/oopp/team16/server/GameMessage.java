package oopp.team16.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameMessage {
    private String type;
    private String sender;
    private Map<String, Object> data;

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
    public GameMessage(String type, String sender) {
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Map<String, Object> getData() {
        return Collections.unmodifiableMap(data);
    }


    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    // Convenience method to add data
    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    @Override
    public String toString() {
        return "GameMessage{" +
            "type='" + type + '\'' +
            ", sender='" + sender + '\'' +
            ", data=" + data +
            '}';
    }
}