package com.anastasiia.geometry;

public class Sphere implements Shape3D {

    private final double radius;

    public Sphere(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius cannot be negative");
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return 4 * Math.PI * (radius * radius);
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public double getVolume() {
        return ((double) 4 / 3) * Math.PI * (radius * radius * radius);
    }

    @Override
    public String toString() {
        return "Sphere: radius=" + radius;
    }
}