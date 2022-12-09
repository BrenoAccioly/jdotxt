package com.todotxt.todotxttouch.task;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FilterFactoryTest {

    @Test
    public void withPriorities() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(Priority.A),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task1 = new Task(0, "(A) Task");
        Task task2 = new Task(0, "(B) Task");

        assertTrue(andFilter.apply(task1));
        assertFalse(andFilter.apply(task2));
    }

    @Test
    public void withoutPriorities() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task = new Task(0, "Task");

        assertTrue(andFilter.apply(task));
    }

    @Test
    public void withContext() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList("c1"),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task1 = new Task(0, "Task @c1");
        Task task2 = new Task(0, "Task @c2");

        assertTrue(andFilter.apply(task1));
        assertFalse(andFilter.apply(task2));
    }

    @Test
    public void withoutContext() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task = new Task(0, "Task");

        assertTrue(andFilter.apply(task));
    }


    @Test
    public void withProject() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList("p1"),
                "",
                true,
                true,
                true
        );
        Task task1 = new Task(0, "Task +p1");
        Task task2 = new Task(0, "Task +p2");

        assertTrue(andFilter.apply(task1));
        assertFalse(andFilter.apply(task2));
    }

    @Test
    public void withoutProject() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task = new Task(0, "Task");

        assertTrue(andFilter.apply(task));
    }

    @Test
    public void withText() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "abc",
                true,
                true,
                true
        );

        Task task1 = new Task(0, "abc");
        Task task2 = new Task(0, "def");

        assertTrue(andFilter.apply(task1));
        assertFalse(andFilter.apply(task2));

    }

    @Test
    public void withoutText() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );
        Task task1 = new Task(0, "abc");
        Task task2 = new Task(0, "def");

        assertTrue(andFilter.apply(task1));
        assertTrue(andFilter.apply(task2));
    }

    @Test
    public void hidden() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                true,
                true
        );

        Task task1 = new Task(0, "Task h:1");
        Task task2 = new Task(0, "Task");

        assertTrue(andFilter.apply(task1));
        assertTrue(andFilter.apply(task2));
    }

    @Test
    public void nonHidden() {
        AndFilter andFilter = (AndFilter) FilterFactory.generateAndFilter(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                "",
                true,
                false,
                true
        );

        Task task1 = new Task(0, "Task h:1");
        Task task2 = new Task(0, "Task");

        assertFalse(andFilter.apply(task1));
        assertTrue(andFilter.apply(task2));
    }

}
