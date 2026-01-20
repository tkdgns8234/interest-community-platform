package com.findy.common.domain;

import lombok.Getter;

@Getter
public class IntegerCounter {
    long count;

    public IntegerCounter() {
        this.count = 0;
    }

    public IntegerCounter(long count) {
        this.count = count;
    }

    public void increase() {
        this.count++;
    }

    public void decrease() {
        if (this.count > 0) {
            this.count--;
        }
    }
}
