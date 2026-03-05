package com.anastasiia.itkacademy.task3;

import java.util.HashMap;
import java.util.Map;

public class Task3 {

    public <T> Map<T, Integer> countOfElements(T[] array) {
        Map<T, Integer> result = new HashMap<>();
        for (T element: array) {
            Integer count = result.getOrDefault(element, 0);
            result.put(element, count + 1);
        }
        return result;
    }
}