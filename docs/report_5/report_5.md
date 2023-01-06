# Assignment 5

### Line and Branch Coverage


The GUI related modules are entirely not exercised by any tests, but some GUI features were tested in the previous assignment. The Task and Util modules have the highest coverage, but still cannot reach more than half of the code and branches in it:

![](https://i.imgur.com/5V7P4SR.png)

![](https://i.imgur.com/OnLdrVY.png)

![](https://i.imgur.com/NdiiNWs.png)

### Additional Tests

The generated were focused on the two main elements of the project, that are not related to the GUI: the `task` and `util` module.

To make it possible to test more parts of the code and more efficiently, we used some distinct features of the JUnit package and also made use of Mockito, as some methods depended on other static values and methods of the GUI module, which made it difficult to test without mocks.

#### Organize In Distinct Test Suites

To separate different methods and its tests, we created a suite with the **@Suite** annotation and defined classes to contain the tests for each method.

```java
@RunWith(Suite.class)
@Suite.SuiteClasses({
        StringsTest.InsertPaddedIfNeededTest.class,
        StringsTest.InsertPaddedTest.class,
        StringsTest.IsBlankTest.class
})
public class StringsTest {

    public static class InsertPaddedIfNeededTest {
        ...
    }

    public static class InsertPaddedTest {
        ...
    }

    public static class IsBlankTest {
        ...
    }
}
```

We used it in some occasions to help with the organization and clarity of the tests.

#### Expected Exceptions

We have also added tests that test for an expected Exception thrown by the in test method. In order to check for an exception in our program, we used the **expected** parameter of the **@Test** annotation:

```java
    @Test(expected = NullPointerException.class)
    public void testPrioritySorterWithNullTask() {
        Sorter<Task> gs = Sorters.PRIORITY.get(true);
        Task t1 = null;
        Task t2 = new Task(1,"Test task!");
        assertEquals(gs.compare(t1,t2),0);
    }
```

#### Parameterized

To easily execute multiple test cases, we run these repetitive tests with the support of the **@Parameterized** annotation family, which conveys an interface to define a collection of inputs and its expected values, and then test it with our test methods:

```java
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
                            Type.NOT_EQUAL,
                            new Task(0, "(A) Task 1"),
                            new Task(0, "(A) Task 1 +project")
                    },
                    // ...
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
        }
    }
```

#### Mockito

Mockito is a testing framework that enable us to create mocks, that can be objects with part of its behaviour modified in order to make it easier to test with other parts of the code that depends on it.

For instance, when creating a task with dates, internally the module would call the `RelativeDate.getRelativeDate` static method, which in turn calls a method from a variable of the GUI module, this variable if not defined, would create an exception and the program would crash. We agreed that this behaviour was a design flaw, and that test should indeed fail, however, in order to increase the coverage and because it was needed in multiple tests, we used the mocks as a workaround for this problem.

```java
    @Test
    public void testArchive() {
        try (MockedStatic<RelativeDate> classMock = mockStatic(RelativeDate.class)) {
            classMock.when(
                () -> RelativeDate.getRelativeDate(
                    any(Date.class)
                )
            ).thenReturn("");
            // ...
        }
    }
```

#### Line and Branch Coverage After Additional tests

After the additional tests, we were able to significantly increase the tests coverage (for this analysis, we have excluded the GUI related module):

![](https://i.imgur.com/jWwf747.png)

![](https://i.imgur.com/GHuQbqc.png)

![](https://i.imgur.com/GVfnVNf.png)

Nevertheless, we were not able to perform more than 90% of both code and branch coverages.
Multiple tests that we have created, while increased the code coverage, did not actually create significant value for the program verification. For instance, we had a method `equals` that was responsible for checking the equality of the **Task** class, and to be able to check every branch of this method, we had to use Java Reflections to modify the values of attributes of this class at runtime, thus increasing the coverage. However, these lines and branches were very unlikely to happen in the normal flow of the program, as the way the data is passed between the objects, some null attribute values were not possible to obtain without changing the internal elements and behaviour of a class. In addition to that, there were multiple methods that relied on static variables and methods from other classes, and extensively testing these methods would require an excessive usage of mocks.
