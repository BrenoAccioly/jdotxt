package com.todotxt.todotxttouch.util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

public class TaskIoTest {

    //readLine method and sWindowsLineBreaks must be set to public in order to test these functions.

    @Test
    public void UnixLineBreakTest() throws NoSuchMethodException {
        StringReader sr = new StringReader("Test string\n");
        BufferedReader reader = new BufferedReader(sr);

        try {
            TaskIo.readLine(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertFalse(TaskIo.sWindowsLineBreaks);
    }

    @Test
    public void WindowsLineBreakTest() throws NoSuchMethodException {
        StringReader sr = new StringReader("Test string\r\n");
        BufferedReader reader = new BufferedReader(sr);

        try {
            TaskIo.readLine(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(TaskIo.sWindowsLineBreaks);
    }

    /* Throws unexpected exception
    @Test
    public void nullBufferTest() throws NoSuchMethodException {

        try {
            TaskIo.readLine(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    */
}
