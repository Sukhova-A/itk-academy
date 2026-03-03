package com.anastasiia.itkacademy.task2;

public class Task {

    public <T> T[] filter(T[] array, Filter<T> obj) {
        T[] result = array.clone();

        for (int i = 0; i < result.length; i++) {
            result[i] = obj.apply(array[i]);
        }
        return result;
    }
}