package com.verbovskiy.client.connection;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static ThreadLocal<Session> instance;
    private final Map<String, Object> attributes;

    private Session() {
        attributes = new HashMap<>();
    }

    public synchronized static ThreadLocal<Session> getInstance() {
        if (instance.get() == null) {
            instance.set(new Session());
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

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void clear() {
        attributes.clear();
    }
}
