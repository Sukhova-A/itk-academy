package com.anastasiia.itkacademy.task5;

public class ComplexTask {

    public void execute() {
        try {
            Thread.sleep(1000);
            System.out.println("ComplexTask successfully done by " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}