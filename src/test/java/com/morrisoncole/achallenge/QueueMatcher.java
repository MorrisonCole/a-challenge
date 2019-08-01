package com.morrisoncole.achallenge;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.arrayContaining;

public class QueueMatcher<T> extends TypeSafeMatcher<Queue<T>> {

    private final T[] expected;

    private QueueMatcher(T[] expected) {
        this.expected = expected;
    }

    @SafeVarargs
    static <V> QueueMatcher<V> matchesQueue(V... expected) {
        return new QueueMatcher<>(expected);
    }

    @Override
    protected boolean matchesSafely(Queue<T> actual) {
        return arrayContaining(expected).matches(queueToArray(actual));
    }

    @Override
    protected void describeMismatchSafely(Queue<T> item, Description mismatchDescription) {
        arrayContaining(expected).describeMismatch(queueToArray(item), mismatchDescription);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(Arrays.toString(expected));
    }

    private Object[] queueToArray(Queue<T> actual) {
        List<T> elements = new ArrayList<>();
        while (!actual.isEmpty()) {
            elements.add(actual.head());
            actual = actual.deQueue();
        }
        return elements.toArray(new Object[0]);
    }
}
