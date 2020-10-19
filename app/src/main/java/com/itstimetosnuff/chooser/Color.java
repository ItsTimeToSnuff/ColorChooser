package com.itstimetosnuff.chooser;

public class Color {
    private String name;
    private int value;

    public Color(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
