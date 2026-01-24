package com.findy.common.domain;

import lombok.Getter;

import java.util.Objects;

@Getter
public class NonNegativeCounter {
    private final long count;

    public NonNegativeCounter() {
        this.count = 0;
    }

    public NonNegativeCounter(long count) {
        this.count = count;
    }

    public NonNegativeCounter increase() {
        return new NonNegativeCounter(count + 1);
    }

    public NonNegativeCounter decrease() {
        return new NonNegativeCounter(Math.max(0, count - 1));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NonNegativeCounter that = (NonNegativeCounter) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(count);
    }
}