package com.verbovskiy.client.entity;

/**
 * The type User.
 *
 * @author Verbovskiy Sergei
 * @version 1.0
 */
public class User implements Entity {
    private String login;
    private String name;
    private String surname;
    private String isBlocked;

    public User(String login, String name, String surname, String isBlocked) {
        this.isBlocked = isBlocked;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getLogin() {
        return login;
    }

    /**
     * set email.
     *
     * @param login the email
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * set name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * set surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        User user = (User) o;

        if (login == null) {
            if (user.login != null) {
                return false;
            }
        } else {
            if (!login.equals(user.login)) {
                return false;
            }
        }
        if (name == null) {
            if (user.name != null) {
                return false;
            }
        } else {
            if (!name.equals(user.name)) {
                return false;
            }
        }
        if (surname == null) {
            if (user.surname != null) {
                return false;
            }
        } else {
            if (!surname.equals(user.surname)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        result += 31 * result + (login == null ? 0 : login.hashCode());
        result += 31 * result + (name == null ? 0 : name.hashCode());
        result += 31 * result + (surname == null ? 0 : surname.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(login);
        builder.append(" ");
        builder.append(name);
        builder.append(" ");
        builder.append(surname);
        return builder.toString();
    }
}