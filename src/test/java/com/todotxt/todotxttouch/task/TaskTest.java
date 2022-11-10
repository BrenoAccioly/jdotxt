package com.todotxt.todotxttouch.task;

import static org.mockito.Mockito.*;

import com.todotxt.todotxttouch.util.StringsTest;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;

import java.net.MalformedURLException;
import java.net.URL;
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
            Task task2 = new Task(1, "Task 3");
            Task task3 = new Task(task2);
            assertEquals(task1.getId(), 0);
            assertEquals(task1.getOriginalText(), "");
            assertFalse(task1.isCompleted());
            assertEquals(task2, task3);
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
