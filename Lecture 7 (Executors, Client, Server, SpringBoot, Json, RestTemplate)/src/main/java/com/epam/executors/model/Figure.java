package com.epam.executors.model;

public class Figure {
    private String name;
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Figure() {
    }

    public Figure(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
