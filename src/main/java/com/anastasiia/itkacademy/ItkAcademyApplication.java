package com.anastasiia.itkacademy;

import java.util.concurrent.ForkJoinPool;

import com.anastasiia.itkacademy.task9.FactorialTask;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItkAcademyApplication {

    public static void main(String[] args) {
        int n = 10; // Вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long result = forkJoinPool.invoke(factorialTask);

        System.out.println("Факториал " + n + "! = " + result);
    }
}