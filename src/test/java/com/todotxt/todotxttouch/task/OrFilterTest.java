package com.todotxt.todotxttouch.task;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class OrFilterTest {
    @Test
    public void orTestTrue() {
        Task task1 = new Task(0, "(A) Task1");
        Task task2 = new Task(1, "Task1 @c1");
        Task task3 = new Task(1, "Task3");
        OrFilter orFilter = new OrFilter();
        ByPriorityFilter byPriorityFilter = new ByPriorityFilter(Arrays.asList(Priority.A));
        ByContextFilter byContextFilter = new ByContextFilter(Arrays.asList("c1"));

        orFilter.addFilter(null);
        orFilter.addFilter(byPriorityFilter);
        orFilter.addFilter(byContextFilter);

        assertTrue(orFilter.apply(task1));
        assertTrue(orFilter.apply(task2));
        assertFalse(orFilter.apply(task3));
    }
}
