package com.todotxt.todotxttouch.task;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ByTextFilterTest {

    @Test
    public void caseSensitive() {
        ByTextFilter byTextFilter = new ByTextFilter("abc", true);

        Task task = new Task(0, "task 1 abc123");
        assertTrue(byTextFilter.apply(task));

        task = new Task(0, "task 1 aBc123");
        assertFalse(byTextFilter.apply(task));

        task = new Task(0, "task 1 123");
        assertFalse(byTextFilter.apply(task));
    }

    @Test
    public void nonCaseSensitive() {
        ByTextFilter byTextFilter = new ByTextFilter("abc", false);

        Task task = new Task(0, "task 1 abc123");
        assertTrue(byTextFilter.apply(task));

        task = new Task(0, "task 1 aBc123");
        assertTrue(byTextFilter.apply(task));

        task = new Task(0, "task 1 123");
        assertFalse(byTextFilter.apply(task));
    }
}
