package com.todotxt.todotxttouch.task;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ByContextFilterTest {

    ByContextFilter byContextFilter;

    @Test
    public void testEmptyContexts() {
        List<String> contexts = new ArrayList<>();
        byContextFilter = new ByContextFilter(contexts);
        Task task = new Task();
        assertTrue(byContextFilter.apply(task));
    }

    @Test
    public void testWithContexts() {
        List<String> contexts = new ArrayList<>();
        byContextFilter = new ByContextFilter(contexts);

        Task task1 = new Task(0, "Task @c1 @c2");
        assertTrue(byContextFilter.apply(task1));

        contexts.add("c1");
        byContextFilter = new ByContextFilter(contexts);
        assertTrue(byContextFilter.apply(task1));

        Task task2 = new Task(0, "Task @c3");
        assertFalse(byContextFilter.apply(task2));
    }

}
