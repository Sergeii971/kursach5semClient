package com.verbovskiy.client.entity;

import java.time.LocalDate;

/**
 * The type Car.
 * Is the subject area of the store.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class Car implements Entity {
    private long carId;
    private CarBrand brand;
    private String model;
    private int manufactureYear;
    private double price;
    private String description;
    private String imageName;
    private LocalDate addedDate;
    private boolean isAvailable;
    private BoxType boxType;
    private CarEngine engineType;
    private CarColor color;

    public Car(long carId, CarBrand brand, String model, int manufactureYear, double price, String description,
               String imageName, LocalDate addedDate, boolean isAvailable, CarColor color, BoxType boxType, CarEngine engineType) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.manufactureYear = manufactureYear;
        this.price = price;
        this.description = description;
        this.imageName = imageName;
        this.addedDate = addedDate;
        this.isAvailable = isAvailable;
        this.color = color;
        this.boxType = boxType;
        this.engineType = engineType;
    }

    /**
     * Gets car id.
     *
     * @return the car id
     */
    public long getCarId() {
        return carId;
    }

    /**
     * Sets car id.
     *
     * @param carId the car id
     */
    public void setCarId(long carId) {
        this.carId = carId;
    }

    /**
     * set brand.
     *
     * @param brand the brand
     */
    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * set description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets image name.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * set imageName.
     *
     * @param imageName the image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets available status.
     *
     * @return boolean
     */
    public boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * set available.
     *
     * @param available  the available
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Gets addded date.
     *
     * @return the added date
     */
    public LocalDate getAddedDate() {
        return addedDate;
    }

    /**
     * set added data.
     *
     * @param addedDate the added date
     */
    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets model.
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets manufacture year.
     *
     * @return the manufacture year
     */
    public int getManufactureYear() {
        return manufactureYear;
    }

    /**
     * set manufacture year.
     *
     * @param manufactureYear the manufacture year
     */
    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    /**
     * Gets brand.
     *
     * @return the brand
     */
    public CarBrand getBrand() {
        return brand;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public CarColor getColor() {
        return color;
    }

    /**
     * set color.
     *
     * @param color the color
     */
    public void setColor(CarColor color) {
        this.color = color;
    }

    /**
     * Gets box type.
     *
     * @return the box type
     */
    public BoxType getBoxType() {
        return boxType;
    }

    /**
     * set box type.
     *
     * @param boxType the box type
     */
    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    /**
     * Gets engine type.
     *
     * @return the engine type
     */
    public CarEngine getEngineType() {
        return engineType;
    }

    /**
     * set engine type.
     *
     * @param engineType the engine type
     */
    public void setEngineType(CarEngine engineType) {
        this.engineType = engineType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Car car = (Car) o;

        if (brand == null) {
            if (car.brand != null) {
                return false;
            }
        } else {
            if (!brand.equals(car.brand)) {
                return false;
            }
        }
        if (model == null) {
            if (car.model != null) {
                return false;
            }
        } else {
            if (!model.equals(car.model)) {
                return false;
            }
        }
        if (color == null) {
            if (car.color != null) {
                return false;
            }
        } else {
            if (!color.equals(car.color)) {
                return false;
            }
        }
        if (engineType == null) {
            if (car.engineType != null) {
                return false;
            }
        } else {
            if (!engineType.equals(car.engineType)) {
                return false;
            }
        }
        if (boxType == null) {
            if (car.boxType != null) {
                return false;
            }
        } else {
            if (!boxType.equals(car.boxType)) {
                return false;
            }
        }
        if (description == null) {
            if (car.description != null) {
                return false;
            }
        } else {
            if (!description.equals(car.description)) {
                return false;
            }
        }
        if (addedDate == null) {
            if (car.addedDate != null) {
                return false;
            }
        } else {
            if (!addedDate.equals(car.addedDate)) {
                return false;
            }
        }
        if (imageName == null) {
            if (car.imageName != null) {
                return false;
            }
        } else {
            if (!imageName.equals(car.imageName)) {
                return false;
            }
        }
        return ((carId == car.carId) && (price == car.price) && (isAvailable == car.isAvailable)
        && (manufactureYear == car.manufactureYear));
    }

    @Override
    public int hashCode() {
        int result = 1;

        result += 31 * result + (brand == null ? 0 : brand.hashCode());
        result += 31 * result + (description == null ? 0 : description.hashCode());
        result += 31 * result + (imageName == null ? 0 : imageName.hashCode());
        result += 31 * result + (addedDate == null ? 0 : addedDate.hashCode());
        result += 31 * result + (color == null ? 0 : color.hashCode());
        result += 31 * result + (boxType == null ? 0 : boxType.hashCode());
        result += 31 * result + (engineType == null ? 0 : engineType.hashCode());
        result += 31 * result + (model == null ? 0 : model.hashCode());
        result += 31 * result + manufactureYear;
        result += 31 * result + Long.hashCode(carId);
        result += 31 * result + Double.hashCode(price);
        result += 31 * result + Boolean.hashCode(isAvailable);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(carId);
        builder.append(" ");
        builder.append(model);
        builder.append(" ");
        builder.append(manufactureYear);
        builder.append(" ");
        builder.append(brand);
        builder.append(" ");
        builder.append(color);
        builder.append(" ");
        builder.append(boxType);
        builder.append(" ");
        builder.append(engineType);
        builder.append(" ");
        builder.append(price);
        builder.append(" ");
        builder.append(description);
        builder.append(" ");
        builder.append(addedDate);
        builder.append(" ");
        builder.append(description);
        builder.append(" ");
        builder.append(isAvailable);
        return builder.toString();
    }
}
