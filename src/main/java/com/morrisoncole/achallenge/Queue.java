package com.morrisoncole.achallenge;

public interface Queue<T> {

    Queue<T> enQueue(T element);

    /**
     * Removes the element at the beginning of the queue, and returns the new queue.
     * @return new queue
     */
    Queue<T> deQueue();

    T head();

    boolean isEmpty();
}
