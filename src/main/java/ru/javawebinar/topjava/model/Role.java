package ru.javawebinar.topjava.model;

public enum Role {
    ROLE_USER(1),
    ROLE_ADMIN(2);

    private int index;

    Role(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}