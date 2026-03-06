package com.anastasiia.itkacademy.task5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ComplexTaskExecutor {
    private final int count;

    public ComplexTaskExecutor(int numberOfTasks) {
        count = numberOfTasks;
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(count);// Создание нового пула
        CyclicBarrier barrier  = new CyclicBarrier(numberOfTasks,
                () -> System.out.println("All tasks are done, last thread:" + Thread.currentThread().getName()));
        List<Future<?>> futures = new ArrayList<>();// Список запущенных Future задач
        for (int i = 0; i < numberOfTasks; i++) {
            Runnable task = () -> {
                ComplexTask complexTask = new ComplexTask();
                try {
                    complexTask.execute();
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            };
            futures.add(executorService.submit(task));
        }
        try {
            for (Future<?> future : futures) {
                future.get();// Запинываем все запущенные Future на завершение и ждем получение результата
            }
        } catch (InterruptedException  | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }
}