package com.verbovskiy.client.entity;

import java.time.LocalDate;

/**
 * The type User order.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class UserOrder implements Entity {
    private long orderId;
    private LocalDate date;
    private final User user;
    private final Car car;
    private boolean inProcessing;

    public UserOrder(long orderId, LocalDate date, User user, Car car, boolean inProcessing) {
        this.orderId = orderId;
        this.date = date;
        this.user = user;
        this.car = car;
        this.inProcessing = inProcessing;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets car.
     *
     * @return the car
     */
    public Car getCar() {
        return car;
    }

    /**
     * Gets is in processing.
     *
     * @return the in processing
     */
    public boolean isInProcessing() {
        return inProcessing;
    }

    /**
     * Sets in processing.
     *
     * @param inProcessing the in processing
     */
    public void setInProcessing(boolean inProcessing) {
        this.inProcessing = inProcessing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        UserOrder order = (UserOrder) o;

        if (date == null) {
            if (order.date != null) {
                return false;
            }
        } else {
            if (!date.equals(order.date)) {
                return false;
            }
        }
        if (user == null) {
            if (order.user != null) {
                return false;
            }
        } else {
            if (!user.equals(order.user)) {
                return false;
            }
        }
        if (car == null) {
            if (order.car != null) {
                return false;
            }
        } else {
            if (!car.equals(order.car)) {
                return false;
            }
        }
        return (orderId == order.orderId && inProcessing == order.inProcessing);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result += 31 * result + (date == null ? 0 : date.hashCode());
        result += 31 * result + (user == null ? 0 : user.hashCode());
        result += 31 * result + (car == null ? 0 : car.hashCode());
        result += 31 * result + Boolean.hashCode(inProcessing);
        result += 31 * result + Long.hashCode(orderId);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(orderId);
        builder.append(" ");
        builder.append(user);
        builder.append(" ");
        builder.append(car);
        builder.append(" ");
        builder.append(date);
        builder.append(" ");
        builder.append(inProcessing);
        return builder.toString();
    }
}
