package com.todotxt.todotxttouch.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CursorPositionCalculatorTest {

    @Test
    public void testCursorPosition() {
        assertEquals(0, CursorPositionCalculator.calculate(0, "abc", null));
        assertEquals(3, CursorPositionCalculator.calculate(0, null, "abc"));
        assertEquals(1, CursorPositionCalculator.calculate(0, "abc", "abcd"));
        assertEquals(0, CursorPositionCalculator.calculate(1, "abc", "ab"));
    }
    @Test
    public void negativeCursorPosition() {
        assertEquals(0, CursorPositionCalculator.calculate(-1, "abc", "ab"));
    }

    @Test
    public void positiveCursorPositionGreaterThanNewValueLength() {
        assertEquals(1, CursorPositionCalculator.calculate(3, "abc", "a"));
    }

    @Test
    public void positiveCursorPositionLowerThanNewValueLength() {
        assertEquals(3, CursorPositionCalculator.calculate(2, "a", "abc"));
    }

}
