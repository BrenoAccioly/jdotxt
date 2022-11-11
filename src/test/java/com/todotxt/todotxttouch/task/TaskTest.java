package com.todotxt.todotxttouch.task;

import static org.mockito.Mockito.*;

import com.todotxt.todotxttouch.util.StringsTest;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TaskTest.TaskTests.class,
        TaskTest.EqualityTest.class
})
public class TaskTest {
    public static class TaskTests {
        @Test
        public void testTaskCreation() {
            Task task1 = new Task();
            Task task2 = new Task(1, "Task 3 due:2022-11-23");
            Task task3 = new Task(task2);
            assertEquals(task1.getId(), 0);
            assertEquals(task1.getOriginalText(), "");
            assertFalse(task1.isCompleted());
            assertEquals(task2, task3);

            assertFalse(task1.isHidden());
            assertEquals("", task1.getPrependedDate());
            assertFalse(task1.isRec());
            assertEquals(0, task1.getDuration());
            assertEquals("", task1.getRelativeAge());
            assertFalse(task1.isFromThreshold());
            assertEquals(0, task1.getAmount());
            assertEquals(new Date(122, Calendar.NOVEMBER, 23), task2.getDueDate());
            assertEquals(Arrays.asList(), task1.getPhoneNumbers());
        }

        @Test
        public void testScreenFormat() {
            Task task1 = new Task(0, "x Task 1");
            assertEquals("x  Task 1", task1.inScreenFormat());
            Task task2 = new Task(0, "Task 2");
            assertEquals("Task 2", task2.inScreenFormat());
        }

        @Test
        public void testInFileFormatHeaderNoDate() {
            Task task1 = new Task(0, "x Task 1");
            assertEquals("x  ", task1.inFileFormatHeaderNoDate());
            Task task2 = new Task(0, "Task 2");
            assertEquals("", task2.inFileFormatHeaderNoDate());
        }

        @Test
        public void testTaskCreationWithFilters() {
            Task task1 = new Task();
            ArrayList<Priority> priorities = new ArrayList<>();
            priorities.add(Priority.A); //priorities.add(Priority.B);
            ArrayList<String> contexts = new ArrayList<>();
            contexts.add( "a");
            ArrayList<String> projects = new ArrayList<>();
            projects.add("b");
            task1.initWithFilters(priorities, contexts, projects);

            assertEquals(task1.getPriority(), Priority.A);
            assertEquals(task1.getContexts(), contexts);
            assertEquals(task1.getProjects(), projects);
        }

        @Test
        public void testTaskDelete() {
            Task taskA = new Task(0, "(A) Task A");
            assertFalse(taskA.isDeleted());
            taskA.delete();
            assertTrue(taskA.isDeleted());
        }

        @Test
        public void testTaskPriority() {
            Task taskA = new Task(0, "(A) Task A");
            Task taskB = new Task(1, "(B) Task B");

            assertEquals(taskA.getPriority().getCode(), "A");
            assertEquals(taskB.getPriority().getCode(), "B");
            assertEquals(taskA.getPriority().inDetailFormat(), "A");
            assertEquals(taskA.getPriority().inFileFormat(), "(A)");

            taskA.setPriority(Priority.C);
            assertEquals(Priority.C, taskA.getPriority());
            assertEquals(taskA.getOriginalPriority(), Priority.A);
        }

        @Test
        public void testTaskProjects() {
            Task taskA = new Task(0, "Task A +p1");
            Task taskB = new Task(1, "Task B +p2 +p3");
            Task taskC = new Task(2, "Task C");

            assertEquals(taskA.getProjects(), Arrays.asList("p1"));
            assertEquals(taskB.getProjects(), Arrays.asList("p2", "p3"));
            assertEquals(taskC.getProjects(), Arrays.asList());
        }

        @Test
        public void testTaskContexts() {
            Task taskA = new Task(0, "Task A @c1");
            Task taskB = new Task(1, "Task B @c2 @c3");
            Task taskC = new Task(2, "Task C");

            assertEquals(taskA.getContexts(), Arrays.asList("c1"));
            assertEquals(taskB.getContexts(), Arrays.asList("c2", "c3"));
            assertEquals(taskC.getContexts(), Arrays.asList());
        }

        @Test
        public void testTaskEmail() {
            Task taskA = new Task(0, "Task A a@domain.com");
            Task taskB = new Task(1, "Task B a@domain.com b@domain.com");
            Task taskC = new Task(2, "Task C");

            assertEquals(taskA.getMailAddresses(), Arrays.asList("a@domain.com"));
            assertEquals(taskB.getMailAddresses(), Arrays.asList("a@domain.com", "b@domain.com"));
            assertEquals(taskC.getMailAddresses(), Arrays.asList());
        }

        @Test
        public void testTaskLinks() {
            Task taskA = new Task(0, "Task A http://www.example.com");
            Task taskB = new Task(1, "Task B http://www.example.com https://www.example.com");
            Task taskC = new Task(2, "Task C");

            try {
                URL url1 = new URL("http://www.example.com");
                URL url2 = new URL("https://www.example.com");
                assertEquals(taskA.getLinks(), Arrays.asList(url1));
                assertEquals(taskB.getLinks(), Arrays.asList(url1, url2));
                assertEquals(taskC.getLinks(), Arrays.asList());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        public void testTaskUpdate() {
            Task task = new Task(0, "x 2022-10-03 Task 1");
            task.update("x 2022-10-04 Task 2");

            assertTrue(task.isCompleted());
            assertEquals(task.getText(), "Task 2");
            assertEquals(task.getCompletionDate(), "2022-10-04");
        }

        @Test
        public void testTaskMarkDone() {
            Task task = new Task(0, "Task 1");
            Date date = new Date();

            assertFalse(task.isCompleted());

            task.markComplete(date);

            assertTrue(task.isCompleted());

            task.markComplete(date);

            assertTrue(task.isCompleted());
        }

        @Test
        public void testTaskMarkUnDone() {
            Task task = new Task(0, "x 2022-10-03 Task 1");

            assertTrue(task.isCompleted());

            task.markIncomplete();

            assertFalse(task.isCompleted());
            assertEquals(task.getCompletionDate(), "");

            task.markIncomplete();

            assertFalse(task.isCompleted());
            assertEquals(task.getCompletionDate(), "");
        }

        @Test
        public void testEqualsNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("completionDate");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("completionDate");
            f2.setAccessible(true);
            f2.set(t2, "a");
            assertNotEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());


        }

        @Test
        public void testEqualsBothNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("completionDate");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("completionDate");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsContextNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("contexts");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("contexts");
            f2.setAccessible(true);
            f2.set(t2, new ArrayList<String>());
            assertNotEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());

        }

        @Test
        public void testEqualsBothURLNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("links");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("links");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsURLNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("links");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("links");
            f2.setAccessible(true);
            f2.set(t2, new ArrayList<URL>());
            assertNotEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothContextNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("contexts");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("contexts");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothMailNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("mailAddresses");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("mailAddresses");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsMailNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("mailAddresses");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("mailAddresses");
            f2.setAccessible(true);
            f2.set(t2, new ArrayList<String>());
            assertNotEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothPhoneNumberNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("phoneNumbers");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("phoneNumbers");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsPhoneNumberNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("phoneNumbers");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("phoneNumbers");
            f2.setAccessible(true);
            f2.set(t2, new ArrayList<String>());
            assertEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothPrependedDateNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("prependedDate");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("prependedDate");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsPrependedDateNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("prependedDate");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("prependedDate");
            f2.setAccessible(true);
            f2.set(t2, "");
            assertNotEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothProjectsNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("projects");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("projects");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsProjectsNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("projects");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("projects");
            f2.setAccessible(true);
            f2.set(t2, new ArrayList<String>());
            assertNotEquals(t1, t2);
            assertNotEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothAgeNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("relativeAge");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("relativeAge");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsAgeNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("relativeAge");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("relativeAge");
            f2.setAccessible(true);
            f2.set(t2, "");
            assertNotEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsBothTextNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("text");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("text");
            f2.setAccessible(true);
            f2.set(t2, null);
            assertEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsTextNull() throws NoSuchFieldException, IllegalAccessException {
            Task t1 = new Task();
            Task t2 = new Task();

            Field f1 = t1.getClass().getDeclaredField("text");
            f1.setAccessible(true);
            f1.set(t1, null);

            Field f2 = t2.getClass().getDeclaredField("text");
            f2.setAccessible(true);
            f2.set(t2, "");
            assertNotEquals(t1, t2);
            assertEquals(t1.hashCode(), t2.hashCode());
        }

        @Test
        public void testEqualsDifferentClass() {
            Task t1 = new Task();
            Object t2 = 1;
            assertFalse(t1.equals(t2));
        }
    }

    @RunWith(Parameterized.class)
    public static class EqualityTest {

        enum Type {EQUAL, NOT_EQUAL};

        @Parameterized.Parameters
        public static Collection input() {
            return Arrays.asList(new Object[][] {
                    {
                            Type.EQUAL,
                            new Task(0, "x (A) Task 1 +project @context"),
                            new Task(0, "x (A) Task 1 +project @context")
                    },
                    {
                            Type.EQUAL,
                            new Task(0, "Task 1 a@domain.com rec:1m"),
                            new Task(0, "Task 1 a@domain.com rec:1m")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "(A) Task 1"),
                            new Task(0, "(A) Task 1 +project")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "(A) Task 1"),
                            new Task(0, "(A) Task 1 @context")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "Task 1 a@domain.com"),
                            new Task(1, "Task 2 rec:1w")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "Task 1 a@domain.com rec:1d"),
                            new Task(0, "Task 1 b@domain.com rec:1y")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "Task 1 http://www.example.com"),
                            new Task(0, "Task 1")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "(A) Task 1"),
                            new Task(0, "(B) Task 1")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "Task 1"),
                            new Task(0, "x 2018-10-01 Task 1"),
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, ""),
                            new Task(0, "Task")
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "x Task 1"),
                            new Task(0, "x 2018-10-01 Task 1"),
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "x 2018-10-01 Task 1"),
                            new Task(0, "x Task 1"),
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, ""),
                            null
                    },
                    {
                            Type.EQUAL,
                            new Task(0, ""),
                            new Task(0, ""),
                    },
                    {
                            Type.NOT_EQUAL,
                            new Task(0, "x 2018-10-02 2018-10-01 Task 1"),
                            new Task(0, "x 2018-10-01 Task 1")
                    },
            });
        }

        @Parameterized.Parameter()
        public Type type;

        @Parameterized.Parameter(1)
        public Task taskInput;

        @Parameterized.Parameter(2)
        public Task taskExpected;

        @Test
        public void equal() {
            Assume.assumeTrue(type==Type.EQUAL);
            assertEquals(taskExpected, taskInput);
            assertEquals(taskExpected.hashCode(), taskInput.hashCode());
        }

        @Test
        public void notEqual() {
            Assume.assumeTrue(type==Type.NOT_EQUAL);
            assertFalse(taskInput.equals(taskExpected));
            if(taskExpected != null)
                assertNotEquals(taskExpected.hashCode(), taskInput.hashCode());
            /*
            Task task1 = new Task(0, "x (A) Task 1 +project @context");
            Task task2 = new Task(0, "x (A) Task 1 +project @context");

            assertFalse(task1.equals(null));
            assertEquals(task1, task1);
            assertEquals(task1, task2);
            assertEquals(task1.hashCode(), task2.hashCode());
            */
        }
    }
}
