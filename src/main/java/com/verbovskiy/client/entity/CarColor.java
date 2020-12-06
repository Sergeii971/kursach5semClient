package com.verbovskiy.client.entity;

/**
 * The enum Car color.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum CarColor {
    BLACK("black"),
    RED("red"),
    WHITE("white"),
    ORANGE("orange");

    CarColor(String color) {
        this.color = color;
    }

    private final String color;

    /**
     * Gets Color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }
}
