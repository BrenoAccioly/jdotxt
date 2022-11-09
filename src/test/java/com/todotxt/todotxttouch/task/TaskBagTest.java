package com.todotxt.todotxttouch.task;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;

public class TaskBagTest {

    TaskBagImpl taskBag;
    LocalFileTaskRepository repository = new LocalFileTaskRepository();

    @Before
    public void setUp() {
        taskBag = new TaskBagImpl(repository);
    }

    @Test
    public void testArchive() {
        taskBag.archive();
        assertEquals(taskBag.size(), 0);

        taskBag.addAsTask("(A) Task 1 +project @context");
        taskBag.archive();

        assertEquals(taskBag.size(), 1);
    }

    @Test
    public void testUnarchive() {
        Task task = new Task(0, "(A) Task 1 +project @context");
        taskBag.unarchive(task);
        assertEquals(taskBag.size(), 1);
        taskBag.addAsTask("(A) Task 1 +project @context");
        taskBag.unarchive(task);
        assertEquals(taskBag.size(), 1);
    }

    @Test
    public void testClear() {
        taskBag.clear();
        assertEquals(taskBag.size(), 0);
        assertFalse(taskBag.hasChanged());

        taskBag.addAsTask("(A) Task 1 +project @context");
        taskBag.clear();

        assertEquals(taskBag.size(), 0);
        assertFalse(taskBag.hasChanged());
    }

    @Test(expected = TaskPersistException.class)
    public void testUpdateUnknownTask() {
        Task task0 = new Task(0, "Task 0");
        taskBag.update(task0);
    }

    @Test
    public void testUpdateKnownTask() {
        Task task0 = new Task(0, "Task 0");
        taskBag.addAsTask("Task 0");
        taskBag.update(task0);
        assertTrue(taskBag.hasChanged());
    }

    @Test
    public void testFilterTasks() {
        List<Priority> priorities = new ArrayList<>();
        List<String> contexts = new ArrayList<>();
        List<String> projects = new ArrayList<>();
        String text = "";
        boolean caseSensitive = false;
        boolean showHidden = true;
        boolean showThreshold = false;

        Filter<Task> filter = FilterFactory.generateAndFilter(
                priorities, contexts, projects, text, caseSensitive, showHidden, showThreshold);

        Comparator<Task> comparator = null;

        ArrayList<Task> tasks = new ArrayList<>();

        assertEquals(taskBag.getTasks(filter, comparator), tasks);

        taskBag.addAsTask("Task 0");
        taskBag.addAsTask("Task 1");
        taskBag.addAsTask("Task 2");
        tasks.add(new Task(0, "Task 0"));
        tasks.add(new Task(1, "Task 1"));
        tasks.add(new Task(2, "Task 2"));

        assertEquals(taskBag.getTasks(filter, comparator), tasks);
    }


    @Test
    public void testDelete() {
        Task task = new Task(0, "Task 0");
        taskBag.delete(task);
        assertEquals(taskBag.size(), 0);
        taskBag.addAsTask("Task 0");
        taskBag.delete(task);
        assertEquals(taskBag.size(), 0);
    }

    @Test
    public void testPriorities() {
        List<Priority> priorities = new ArrayList<>();
        assertEquals(taskBag.getPriorities(), priorities);

        taskBag.addAsTask("(A) Task 0");
        taskBag.addAsTask("(B) Task 1");
        taskBag.addAsTask("(C) Task 2");

        priorities.add(Priority.A);
        priorities.add(Priority.B);
        priorities.add(Priority.C);

        assertEquals(taskBag.getPriorities(), priorities);
    }

    @Test
    public void testContexts() {
        List<String> contexts = new ArrayList<>();
        assertEquals(taskBag.getContexts(false), contexts);
        contexts.add("-");
        assertEquals(taskBag.getContexts(true), contexts);

        taskBag.addAsTask("Task 0");
        taskBag.addAsTask("Task 1 @a");
        taskBag.addAsTask("Task 2 @b");

        contexts.add("a");
        contexts.add("b");

        assertEquals(taskBag.getPriorities(), contexts);
    }

    @Test
    public void testProjects() {
        List<String> projects = new ArrayList<>();
        assertEquals(taskBag.getProjects(false), projects);
        projects.add("-");
        assertEquals(taskBag.getContexts(true), projects);

        taskBag.addAsTask("Task 0");
        taskBag.addAsTask("Task 1 +a");
        taskBag.addAsTask("Task 2 +b");

        projects.add("a");
        projects.add("b");

        assertEquals(taskBag.getPriorities(), projects);
    }

}
