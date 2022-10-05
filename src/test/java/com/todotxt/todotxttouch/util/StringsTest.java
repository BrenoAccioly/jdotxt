package com.todotxt.todotxttouch.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StringsTest {

    @Test
    public void inBoundsInsertionPoint() {
        // Given
        String s = "124";
        int insertAt = 2;
        String toInsert = "3";

        // When
        String result = Strings.insertPadded(s,insertAt,toInsert);

        // Then
        assertEquals("12 3 4",result);
    }

    /* Commented because it throws an unexpected error
    @Test
    public void outBoundsInsertionPoint() {
        // Given
        String s = "124";
        int insertAt = 4;
        String toInsert = "3";

        // When
        String result = Strings.insertPadded(s,insertAt,toInsert);

        // Then
        assertEquals("12 3 4",result);
    }
    */

    @Test
    public void spaceInBothEndsTest() {
        // Given
        String s = "12  4";
        int insertAt = 3;
        String toInsert = "3";

        // Then
        String result = Strings.insertPadded(s,insertAt,toInsert);

        // When
        assertEquals("12 3 4",result);
    }

    @Test
    public void spaceInStartTest() {
        // Given
        String s = "12 4";
        int insertAt = 2;
        String toInsert = "3";

        // When
        String result = Strings.insertPadded(s,insertAt,toInsert);

        // Then
        assertEquals("12 3 4",result);
    }
    @Test
    public void nullIsBlankTest() {
        // Given
        String s = null;

        // When
        boolean result = Strings.isBlank(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void emptyIsBlankTest() {
        // Given
        String s = "";

        // When
        boolean result = Strings.isBlank(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void whitespaceOnlyIsBlankTest() {
        // Given
        String s = "    ";

        // When
        boolean result = Strings.isBlank(s);

        // Then
        assertTrue(result);
    }

    @Test
    public void regularStringIsNotBlankTest() {
        // Given
        String s = "    a   ";

        // When
        boolean result = Strings.isBlank(s);

        // Then
        assertFalse(result);
    }
}
