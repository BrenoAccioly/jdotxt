package com.todotxt.todotxttouch.task;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

public class PriorityTest {
    @Test
    public void testRange() {
        assertEquals(Arrays.asList(Priority.B, Priority.A), Priority.range(Priority.B, Priority.A));
        assertEquals(Arrays.asList(Priority.A), Priority.range(Priority.A, Priority.A));
        assertEquals(Arrays.asList(Priority.A, Priority.B, Priority.C), Priority.range(Priority.A, Priority.C));
    }

    @Test
    public void testRangeInCode() {
        assertEquals(Arrays.asList("B", "A"), Priority.rangeInCode(Priority.B, Priority.A));
        assertEquals(Arrays.asList("A"), Priority.rangeInCode(Priority.A, Priority.A));
        assertEquals(Arrays.asList("A", "B", "C"), Priority.rangeInCode(Priority.A, Priority.C));
    }

    @Test
    public void testInCode() {
        assertEquals(Arrays.asList("A"), Priority.inCode(Arrays.asList(Priority.A)));
        assertEquals(Arrays.asList("A", "C"), Priority.inCode(Arrays.asList(Priority.A, Priority.C)));
        assertEquals(Arrays.asList(), Priority.inCode(Arrays.asList()));
    }

    @Test
    public void testToPriority() {
        assertEquals(Arrays.asList(Priority.A, Priority.B), Priority.toPriority(Arrays.asList("A", "B")));
        assertEquals(Arrays.asList(), Priority.toPriority(Arrays.asList()));
        assertEquals(Arrays.asList(Priority.NONE), Priority.toPriority(Arrays.asList("?")));
        assertEquals(Priority.NONE, Priority.toPriority((String) null));
    }
}
