package com.anastasiia.geometry;

public class Cube implements Shape3D {

    private final double side;

    public Cube(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side must be positive");
        }
        this.side = side;
    }

    @Override
    public double getArea() {
        return 6 * side * side;
    }

    @Override
    public double getPerimeter() {
        return 12 * side;
    }

    @Override
    public double getVolume() {
        return side * side * side;
    }

    @Override
    public String toString() {
        return "Cube: side=" + side;
    }
}