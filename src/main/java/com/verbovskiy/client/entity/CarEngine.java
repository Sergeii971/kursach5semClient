package com.verbovskiy.client.entity;

/**
 * The enum Car engine.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum CarEngine {
    DIESEL("diesel"),
    PETROL("petrol");

    private final String engine;

    CarEngine(String engine) {
        this.engine = engine;
    }

    /**
     * Gets engine.
     *
     * @return the engine
     */
    public String getEngine() {
        return engine;
    }
}
