package com.verbovskiy.client.entity;

/**
 * The enum Car brand.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public enum CarBrand {
    AUDI("Audi"),
    BMW("BMW"),
    BUGATTI("Bugatti"),
    BENTLEY("Bentley"),
    CADILLAC("Cadillac"),
    NISSAN("Nissan"),
    FERRARI("Ferrari"),
    JAGUAR("Jaguar"),
    MASERATI("Maserati");

    private final String brand;

    CarBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets Brand.
     *
     * @return the Brand
     */
    public String getBrand() {
        return brand;
    }
}

