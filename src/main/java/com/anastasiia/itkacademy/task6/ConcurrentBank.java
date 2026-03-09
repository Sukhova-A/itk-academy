package com.anastasiia.itkacademy.task6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConcurrentBank {

    private final List<BankAccount> accounts = Collections.synchronizedList(new ArrayList<>());

    public BankAccount createAccount(int startAmount) {
        if (startAmount < 0) {
            throw new IllegalArgumentException();
        }
        BankAccount account = new BankAccount(startAmount);
        accounts.add(account);
        return account;
    }

    public synchronized void transfer(BankAccount from, BankAccount to, int amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }

    public int getTotalBalance() {
        synchronized (accounts) {
            int result = 0;
            for (BankAccount account : accounts) {
                result += account.getBalance();
            }
            return result;
        }
    }
}