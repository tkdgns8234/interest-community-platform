package com.findy.common.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PositiveIntegerCounter {
    private final long count;

    public PositiveIntegerCounter() {
        this.count = 0;
    }

    public PositiveIntegerCounter(long count) {
        this.count = count;
    }

    public PositiveIntegerCounter increase() {
        return new PositiveIntegerCounter(count + 1);
    }

    public PositiveIntegerCounter decrease() {
        return new PositiveIntegerCounter(Math.max(0, count - 1));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PositiveIntegerCounter that = (PositiveIntegerCounter) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(count);
    }
}