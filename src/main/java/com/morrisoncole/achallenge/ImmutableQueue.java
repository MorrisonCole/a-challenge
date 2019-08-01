package com.morrisoncole.achallenge;

import java.util.Arrays;

public class ImmutableQueue<T> implements Queue<T> {

    private final Object[] data;

    public ImmutableQueue() {
        this(new Object[0]);
    }

    public ImmutableQueue(Object... data) {
        this.data = data;
    }

    @Override
    public Queue<T> enQueue(T element) {
        int elementCount = data.length;

        Object[] newQueue = Arrays.copyOf(this.data, elementCount + 1);
        newQueue[elementCount] = element;

        return new ImmutableQueue<>(newQueue);
    }

    @Override
    public Queue<T> deQueue() {
        if (data.length <= 1) {
            return new ImmutableQueue<>();
        }

        return new ImmutableQueue<>(Arrays.copyOfRange(data, 1, data.length));
    }

    @Override
    public T head() {
        return (T) data[0];
    }

    @Override
    public boolean isEmpty() {
        return data.length == 0;
    }
}
