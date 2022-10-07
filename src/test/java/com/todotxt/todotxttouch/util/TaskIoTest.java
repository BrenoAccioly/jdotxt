package com.todotxt.todotxttouch.util;

import com.todotxt.todotxttouch.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TaskIoTest.ReadLineTest.class, TaskIoTest.LoadTasksTest.class })
public class TaskIoTest {

    public static class ReadLineTest {

        // readLine method and sWindowsLineBreaks must be set to public in order to test these functions.

        @Test
        public void UnixLineBreakTest() {
            // Given
            StringReader sr = new StringReader("Test string\n");
            BufferedReader reader = new BufferedReader(sr);
            String returnString;
            // When
            try {
                returnString = TaskIo.readLine(reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Then
            assertFalse(TaskIo.sWindowsLineBreaks);
            assertEquals(returnString, "Test string\n");
        }

        @Test
        public void WindowsLineBreakTest() {
            // Given
            StringReader sr = new StringReader("Test string\r\n");
            BufferedReader reader = new BufferedReader(sr);
            String returnString;
            // When
            try {
                returnString = TaskIo.readLine(reader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Then
            assertTrue(TaskIo.sWindowsLineBreaks);
            assertEquals(returnString, "Test string\r\n");
        }

        // Throws unexpected exception
        @Test
        public void nullBufferTest() {
            try {
                TaskIo.readLine(null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class LoadTasksTest {
        // Throws unexpected exception
        @Test
        public void loadNullFile() {
            // Given
            File file = null;
            // When
            try {
                TaskIo.loadTasksFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        public void loadNonExistentFile() {
            // Given
            File file = new File("./unknownFile");
            ArrayList<Task> tasks = null;
            // When
            try {
                tasks = TaskIo.loadTasksFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Then
            assertTrue(tasks.isEmpty());
        }

        @Test
        public void loadNotEmptyFile() {
            // Given
            Task task1 = new Task(0, "x 2022-10-03 Task 1");
            Task task2 = new Task(1, "x 2022-10-09 Task 2");
            ArrayList<Task> tasksToWrite = new ArrayList<>();
            tasksToWrite.add(task1);
            tasksToWrite.add(task2);
            File file = new File("./tasksFile");
            TaskIo.writeToFile(tasksToWrite, file);
            ArrayList<Task> tasksResult = null;

            // When
            try {
                tasksResult = TaskIo.loadTasksFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Then
            assertEquals(tasksResult.size(), 2);
            assertEquals(tasksResult.get(0), task1);
            assertEquals(tasksResult.get(1), task2);

            file.delete();
        }

        @Test
        public void loadEmptyFile() {
            // Given
            ArrayList<Task> tasksToWrite = new ArrayList<>();
            File file = new File("./emptyTasksFile");
            TaskIo.writeToFile(tasksToWrite, file);
            ArrayList<Task> tasksResult = null;

            // When
            try {
                tasksResult = TaskIo.loadTasksFromFile(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Then
            assertEquals(tasksResult.size(), 0);

            file.delete();
        }
    }
}
