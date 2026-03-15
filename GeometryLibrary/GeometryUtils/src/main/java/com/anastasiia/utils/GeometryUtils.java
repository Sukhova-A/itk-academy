package com.anastasiia.utils;

import com.anastasiia.geometry.Shape;

public class GeometryUtils {

    public static boolean isLarger(Shape a, Shape b) {
        return a.getArea() > b.getArea();
    }

    public static double convertToCm(double value, String unit) {
        return switch (unit.toLowerCase()) {
            case "mm" -> value / 10;
            case "m"  -> value * 100;
            default   -> value;
        };
    }
}