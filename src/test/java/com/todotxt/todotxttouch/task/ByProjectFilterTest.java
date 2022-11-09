package com.todotxt.todotxttouch.task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ByProjectFilterTest {
    ByProjectFilter byProjectFilter;

    @Test
    public void testEmptyContexts() {
        List<String> projects = new ArrayList<>();
        byProjectFilter = new ByProjectFilter(projects);
        Task task = new Task();
        assertTrue(byProjectFilter.apply(task));
    }

    @Test
    public void testWithContexts() {
        List<String> projects = new ArrayList<>();
        byProjectFilter = new ByProjectFilter(projects);

        Task task1 = new Task(0, "Task +c1 +c2");
        assertTrue(byProjectFilter.apply(task1));

        projects.add("c1");
        byProjectFilter = new ByProjectFilter(projects);
        assertTrue(byProjectFilter.apply(task1));

        Task task2 = new Task(0, "Task +c3");
        assertFalse(byProjectFilter.apply(task2));
    }
}
