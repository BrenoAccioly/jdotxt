package com.todotxt.todotxttouch.util;

import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilTest {

    // Throws unexpected exception
    @Test
    public void nullIntegerList2IntArray() {
        // Given
        List<Integer> list = null;

        // Then
        int[] array =  Util.integerList2IntArray(list);

        // When
        assertEquals(array.length, 0);
    }

    @Test
    public void emptyIntegerList2IntArray() {
        // Given
        List<Integer> list = new ArrayList<>();

        // Then
        int[] array =  Util.integerList2IntArray(list);

        // When
        assertEquals(array.length, 0);
    }

    @Test
    public void notEmptyIntegerList2IntArray() {
        // Given
        List<Integer> list = Arrays.asList(1, 2, 3);

        // Then
        int[] array =  Util.integerList2IntArray(list);

        // When
        assertEquals(array.length, 3);
        assertEquals(array[0], 1);
        assertEquals(array[1], 2);
        assertEquals(array[2], 3);
    }

    @Test
    public void resourceExistsTest() {
        String resourcePath = "/drawable/about.png";
        ImageIcon icon = Util.createImageIcon(resourcePath);
        assertNotEquals(null,icon);
    }

    @Test
    public void resourceDoesNotExistsTest() {
        String resourcePath = "/drawable/UNDEFINED.png";
        ImageIcon icon = Util.createImageIcon(resourcePath);
        assertEquals(null,icon);
    }
}
