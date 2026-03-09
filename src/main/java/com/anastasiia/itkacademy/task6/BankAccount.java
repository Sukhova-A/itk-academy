package com.anastasiia.itkacademy.task6;

import java.util.concurrent.atomic.AtomicInteger;

public class BankAccount {

    private final AtomicInteger balance;

    public BankAccount(int startAmount) {
        if (startAmount < 0) {
            throw new IllegalArgumentException("Amount must be non‑negative");
        }
        balance = new AtomicInteger(startAmount);
    }

    public void deposit(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non‑negative");
        }
        balance.addAndGet(amount);
    }

    public void withdraw(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non‑negative");
        }
        balance.getAndUpdate(current -> {
            if (current < amount) {
                throw new IllegalArgumentException("Insufficient funds");
            }
            return current - amount;
        });
    }

    public int getBalance() {
        return balance.get();
    }
}