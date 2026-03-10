package com.anastasiia.itkacademy.task9;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {


    private final int start;
    private final int end;

    private static final int PARTS = 4;

    public FactorialTask(int n) {
        this(1, n);
    }

    private FactorialTask(int start, int end) {
        this.start = start;
        this.end   = end;
    }

    @Override
    protected Long compute() {
        int length = end - start + 1;
        if (length <= PARTS) { // Когда не надо делить на подзадачи
            return intervalFactorial(start, end);
        }

        List<FactorialTask> subtasks = new ArrayList<>(); // Список подзадач

        for (int from = start; from <= end; from += PARTS) {
            int to = Math.min(from + PARTS - 1, end);
            FactorialTask subTask = new FactorialTask(from, to);
            subTask.fork(); // Отправляем подзадачу в пул
            subtasks.add(subTask);
        }

        long result = 1L;
        for (FactorialTask subTask : subtasks) {
            long subPart = subTask.join(); // Получаем результаты подзадач
            result *= subPart;
        }
        return result;
    }

    private long intervalFactorial(int from, int to) {
        long result = 1;
        for (int i = from; i <= to; i++) {
            result *= i;
        }
        return result;
    }
}