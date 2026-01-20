package com.findy.common.domain;

public class IntegerCounter {
    int count;

    public IntegerCounter() {
        this.count = 0;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if (this.count > 0) {
            this.count--;
        }
    }

    public int getCount() {
        return count;
    }
}
