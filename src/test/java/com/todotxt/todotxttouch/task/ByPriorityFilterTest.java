package com.todotxt.todotxttouch.task;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class ByPriorityFilterTest {

    @Test
    public void noPrioritiesMatch() {
        ByPriorityFilter filter = new ByPriorityFilter(Collections.singletonList(Priority.A));
        Task task1 = new Task(1, "(B) Task");
        Task task2 = new Task(1, "Task");

        assertFalse(filter.apply(task1));
        assertFalse(filter.apply(task2));
    }

    @Test
    public void prioritiesMatch() {
        ByPriorityFilter filter = new ByPriorityFilter(Collections.singletonList(Priority.B));
        Task task1 = new Task(1, "(B) Task");

        assertTrue(filter.apply(task1));

        filter = new ByPriorityFilter(Collections.emptyList());
        assertTrue(filter.apply(task1));
    }
}
