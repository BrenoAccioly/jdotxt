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

    //Task task;

    //@Spy private Task taskSpy = Mockito.spy(task);

    /*
    *       Project spy = Mockito.spy(project);
      Mockito.doReturn(10).when(spy).getElapsedSeconds();
      *
      *    Field reader = JTimeSchedApp.class.getDeclaredField("LOGGER");
    reader.setAccessible(true);
    JTimeSchedApp mainClass = new JTimeSchedApp();
    Logger l = Logger.getLogger("JTimeSched");
    l.setLevel(Level.ALL);
    reader.set(mainClass, l);
      *
      * public static LanguagesController lang;
    * */
    @Test
    public void testArchive() {
        /*
        try {
            Field lang =  JdotxtGUI.class.getDeclaredField("lang");
            LanguagesController languagesController = new LanguagesController("English");
            lang.setAccessible(true);
            JdotxtGUI jdotxtGUI = new JdotxtGUI();
            lang.set(jdotxtGUI, languagesController);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        */
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            //Date any = new Date();
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");

            //assertEquals(null, RelativeDate.getRelativeDate(any(Date.class)));

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
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
            Task task = new Task(0, "(A) Task 1 +project @context");
            taskBag.unarchive(task);
            assertEquals(taskBag.size(), 1);

            taskBag.addAsTask("(A) Task 1 +project @context");
            taskBag.unarchive(task);
            assertEquals(taskBag.size(), 1);
        }
    }

    @Test
    public void testClear() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
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
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
            Task task0 = new Task(0, "Task 0");
            taskBag.addAsTask("Task 0");
            taskBag.update(task0);
            assertTrue(taskBag.hasChanged());
        }
    }

    @Test
    public void testFilterTasks() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
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
    }

    @Test
    public void testDelete() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
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
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
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
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
            List<String> contexts = new ArrayList<>();
            assertEquals(taskBag.getContexts(false), contexts);
            contexts.add("-");
            assertEquals(taskBag.getContexts(true), contexts);

            taskBag.addAsTask("Task 0");
            taskBag.addAsTask("Task 1 @a");
            taskBag.addAsTask("Task 2 @b");

            contexts.add("a");
            contexts.add("b");

            assertEquals(Arrays.asList("-", "a", "b"), contexts);
        }

    }

    @Test
    public void testProjects() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(() -> RelativeDate.getRelativeDate(any(Date.class))).thenReturn("bar");
            List<String> projects = new ArrayList<>();
            assertEquals(taskBag.getProjects(false), projects);
            projects.add("-");
            assertEquals(taskBag.getContexts(true), projects);

            taskBag.addAsTask("Task 0");
            taskBag.addAsTask("Task 1 +a");
            taskBag.addAsTask("Task 2 +b");

            projects.add("a");
            projects.add("b");

            assertEquals(Arrays.asList("-", "a", "b"), projects);
        }
    }
}
