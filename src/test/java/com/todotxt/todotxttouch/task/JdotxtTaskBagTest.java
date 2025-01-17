package com.todotxt.todotxttouch.task;

import com.todotxt.todotxttouch.util.RelativeDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class JdotxtTaskBagTest {
    JdotxtTaskBagImpl taskBag;
    LocalFileTaskRepository repository = new LocalFileTaskRepository();

    @Before
    public void setUp() {
        taskBag = new JdotxtTaskBagImpl(repository);
    }

    @Test
    public void testArchive() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");

            taskBag.archive();
            assertEquals(taskBag.size(), 0);

            taskBag.addAsTask("(A) Task 1 +project @context");
            taskBag.archive();

            assertEquals(taskBag.size(), 1);
        }
    }

    @Test
    public void testUnarchive() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            Task task = new Task(0, "(A) Task 1 +project @context");
            taskBag.unarchive(task);
            assertEquals(1, taskBag.size());

            taskBag.addAsTask("(A) Task 1 +project @context");
            taskBag.unarchive(task);
            assertEquals(1, taskBag.size());

            task = new Task(3, "(A) Task 1 +project @context");
            taskBag.unarchive(task);

            task = new Task(-1, "(A) Task 1 +project @context");
            taskBag.unarchive(task);
        }
    }

    @Test
    public void testClear() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            taskBag.clear();
            assertEquals(taskBag.size(), 0);
            assertFalse(taskBag.hasChanged());

            taskBag.addAsTask("(A) Task 1 +project @context");
            taskBag.clear();

            assertEquals(taskBag.size(), 0);
            assertFalse(taskBag.hasChanged());
        }
    }

    @Test(expected = TaskPersistException.class)
    public void testUpdateUnknownTask() {
        Task task0 = new Task(0, "Task 0");
        taskBag.update(task0);
    }

    @Test
    public void testUpdateKnownTask() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            Task task0 = new Task(0, "Task 0");
            taskBag.addAsTask("Task 0");
            taskBag.update(task0);
            assertTrue(taskBag.hasChanged());
        }
    }

    @Test
    public void testFilterTasks() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
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

            assertEquals(Arrays.asList(), taskBag.getTasks(filter, comparator));

            taskBag.addAsTask("2022-11-10 Task 0 t:2022-11-10");
            taskBag.addAsTask("2022-11-10 Task 1 t:2022-11-10");
            taskBag.addAsTask("2022-11-10 Task 2 t:2022-11-10");

            assertEquals(
                    Arrays.asList(
                            new Task(0, "2022-11-10 Task 0 t:2022-11-10"),
                            new Task(1, "2022-11-10 Task 1 t:2022-11-10"),
                            new Task(2, "2022-11-10 Task 2 t:2022-11-10")
                    ),
                    taskBag.getTasks(filter, comparator)
            );
        }
    }

    @Test
    public void testDelete() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            Task task = new Task(0, "Task 0");
            taskBag.addAsTask("Task 0");
            assertEquals(taskBag.size(), 1);
            taskBag.delete(task);
            assertEquals(taskBag.size(), 0);
            taskBag.addAsTask("Task 0");
            taskBag.delete(task);
            assertEquals(taskBag.size(), 0);
        }
    }

    @Test
    public void testPriorities() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            List<Priority> priorities = new ArrayList<>();
            assertEquals(priorities, taskBag.getPriorities());

            taskBag.addAsTask("(A) Task 0");
            taskBag.addAsTask("(B) Task 1");
            taskBag.addAsTask("(C) Task 2");
            taskBag.addAsTask("Task 3");

            assertEquals(Arrays.asList(
                    Priority.A,
                    Priority.B,
                    Priority.C,
                    Priority.NONE
            ), taskBag.getPriorities());
        }
    }

    @Test
    public void testContexts() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            assertEquals(Arrays.asList(), taskBag.getContexts(false));

            taskBag.addAsTask("Task 0");
            taskBag.addAsTask("Task 1 @a");
            taskBag.addAsTask("Task 2 @b");

            assertEquals(Arrays.asList("-", "a", "b"), taskBag.getContexts(true));
        }
    }

    @Test
    public void testProjects() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("");
            assertEquals(Arrays.asList(), taskBag.getProjects(false));
            assertEquals(Arrays.asList("-"), taskBag.getProjects(true));

            taskBag.addAsTask("Task 0");
            taskBag.addAsTask("Task 1 +a");
            taskBag.addAsTask("Task 2 +b");

            assertEquals(Arrays.asList("-", "a", "b"), taskBag.getProjects(true));
        }
    }
}
