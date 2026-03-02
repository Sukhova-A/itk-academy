package com.anastasiia.itkacademy.task1;

import java.util.ArrayList;
import java.util.List;

public class HistoryCaretaker {
    private final List<MyStringBuilder.Snapshot> history = new ArrayList<>();

    public void push(MyStringBuilder.Snapshot snapshot) {
        history.add(snapshot);
    }

    public MyStringBuilder.Snapshot pop() {
        if (history.isEmpty()) {
            return null;
        }
        return history.remove(history.size() - 1);
    }
}