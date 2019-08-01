package com.morrisoncole.achallenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.morrisoncole.achallenge.QueueMatcher.matchesQueue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ImmutableQueueTest {

    private static final int AN_ELEMENT = 42;
    private static final int ANOTHER_ELEMENT = 66;

    private ImmutableQueue<Integer> emptyQueue;

    @BeforeEach
    void setUp() {
        emptyQueue = new ImmutableQueue<>();
    }

    @Test
    void newQueueIsEmpty() {
        assertThat(emptyQueue.isEmpty(), equalTo(true));
    }

    @Test
    void queueWithAtLeastOneElementIsNotEmpty() {
        Queue<Integer> result = emptyQueue.enQueue(1);
        assertThat(result.isEmpty(), equalTo(false));
    }

    @Test
    void enqueuesElement() {
        Queue<Integer> result = emptyQueue.enQueue(AN_ELEMENT);
        assertThat(result, matchesQueue(AN_ELEMENT));
    }

    @Test
    void dequeuesElement() {
        ImmutableQueue<Integer> queue = new ImmutableQueue<>(1, 2, 3);

        Queue<Integer> result = queue.deQueue();

        assertThat(result, matchesQueue(2, 3));
    }

    @Test
    void returnsHead() {
        ImmutableQueue<Integer> queue = new ImmutableQueue<>(AN_ELEMENT, 2, 3);

        assertThat(queue.head(), equalTo(AN_ELEMENT));
    }

    @Test
    void operationsDoNotAffectOriginalQueue() {
        ImmutableQueue<Integer> originalQueue = new ImmutableQueue<>(4, 7, 7);

        originalQueue.enQueue(AN_ELEMENT);
        originalQueue.enQueue(ANOTHER_ELEMENT);
        originalQueue.deQueue();
        originalQueue.head();
        originalQueue.isEmpty();

        assertThat(originalQueue, matchesQueue(4, 7, 7));
    }
}