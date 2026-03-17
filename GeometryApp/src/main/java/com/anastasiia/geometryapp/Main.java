package com.anastasiia.geometryapp;

import com.anastasiia.geometry.Circle;
import com.anastasiia.geometry.Rectangle;
import com.anastasiia.geometry.Triangle;
import com.anastasiia.utils.GeometryUtils;

public class Main {
    public static void main(String[] args) {

        Circle circle = new Circle(5.0);
        Rectangle rectangle = new Rectangle(4.0, 6.0);
        Triangle triangle = new Triangle(3.0, 4.0, 5.0);

        System.out.println("Circle: " + circle);
        System.out.println("Area: " + circle.getArea());
        System.out.println("Perimeter: " + circle.getPerimeter());
        System.out.println();

        System.out.println("Rectangle: " + rectangle);
        System.out.println("Area: " + rectangle.getArea());
        System.out.println("Perimeter: " + rectangle.getPerimeter());
        System.out.println("Circle larger than Rectangle: " + GeometryUtils.isLarger(circle, rectangle));
        System.out.println();

        System.out.println("Triangle: " + triangle);
        System.out.println("Area: " + triangle.getArea());
        System.out.println("Perimeter: " + triangle.getPerimeter());
    }
}