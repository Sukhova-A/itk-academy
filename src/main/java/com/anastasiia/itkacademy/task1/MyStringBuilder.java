package com.anastasiia.itkacademy.task1;

import java.util.Arrays;

public class MyStringBuilder {

    private byte[] value;
    private int length = 0;
    private final HistoryCaretaker caretaker = new HistoryCaretaker();

    public MyStringBuilder() {
        value = new byte[16];
    }

    public MyStringBuilder append(String str) {
        if (str == null) {
            return append("null");
        }
        save();

        byte[] bytes = str.getBytes();
        ensureCapacity(length + bytes.length);
        System.arraycopy(bytes, 0, value, length, bytes.length);
        length += bytes.length;
        return this;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= value.length) {
            return;
        }
        int newCapacity = Math.max(value.length * 2, minCapacity);
        value = Arrays.copyOf(value, newCapacity);
    }

    public MyStringBuilder delete(int start, int end) {
        if (start < 0 || start > end || end > length) {
            throw new IndexOutOfBoundsException(
                    "start=" + start + ", end=" + end + ", length=" + length);
        }
        save();
        int len = end - start;
        if (len > 0) {
            int count = length - end;
            System.arraycopy(value, end, value, start, count);
        }
        length -= (end - start);
        return this;
    }

    public MyStringBuilder undo() {
        restore();
        return this;
    }

    private void save() {
        caretaker.push(new Snapshot(value.clone(), length));
    }

    private void restore() {
        Snapshot snapshot = caretaker.pop();
        value = snapshot.val;
        length = snapshot.len;
    }

    public static class Snapshot {
        final byte[] val;
        final int len;
        Snapshot(byte[] val, int len) {
            this.val = val;
            this.len = len;
        }
    }
}