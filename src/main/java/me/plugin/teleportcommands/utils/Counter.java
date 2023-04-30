package me.plugin.teleportcommands.utils;

public class Counter {
    private int count = 0;

    public Counter(int count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }
    public void increment(int i) {
        count += i;
    }
    public void decrement() {
        count--;
    }
    public void decrement(int i) {
        count -= i;
    }
}
