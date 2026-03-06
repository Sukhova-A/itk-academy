package com.anastasiia.itkacademy.task4;

public class BlockingQueue<T> {

    private final Object[] elements;
    private int count;
    private int head;
    private int tail;

    public BlockingQueue(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        elements = new Object[capacity];
    }

    public synchronized void enqueue(T element) throws InterruptedException {
        while (count >= elements.length) {
            wait();
        }
        elements[head] = element;
        count++;
        head = (head + 1) % elements.length;
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (count <= 0) {
            wait();
        }
        T result = (T) elements[tail];
        elements[tail] = null;
        count--;
        tail = (tail + 1) % elements.length;
        notifyAll();
        return result;
    }

    public int size() {
        return count;
    }
}