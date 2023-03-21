package com.verbovskiy.client.connection;

import java.util.HashMap;
import java.util.Map;

public class ServerResponse {
    private static ThreadLocal<ServerResponse> instance;
    private final Map<String, Object> attributes;

    private ServerResponse() {
        attributes = new HashMap<>();
    }

    static synchronized ThreadLocal<ServerResponse> getInstance() {
        if (instance.get() == null) {
            instance.set(new ServerResponse());
        }
        return instance;
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAllAttributes(Map<String, Object> attributes) {
        this.attributes.putAll(attributes);
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void clear() {
        attributes.clear();
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }
}
