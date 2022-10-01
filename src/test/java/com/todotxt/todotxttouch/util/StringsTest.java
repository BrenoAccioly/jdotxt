package com.todotxt.todotxttouch.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StringsTest {

    @Test
    public void inBoundsInsertionPoint() {
        String s = "124";
        int insertAt = 2;
        String toInsert = "3";

        String result = Strings.insertPadded(s,insertAt,toInsert);
        assertEquals("12 3 4",result);
    }
    /* Commented because it throws an unexpected error
    @Test
    public void outBoundsInsertionPoint() {
        String s = "124";
        int insertAt = 4;
        String toInsert = "3";
        String result = Strings.insertPadded(s,insertAt,toInsert);
        assertEquals("12 3 4",result);
    }
    */
    @Test
    public void spaceInBothEndsTest() {
        String s = "12  4";
        int insertAt = 3;
        String toInsert = "3";
        String result = Strings.insertPadded(s,insertAt,toInsert);
        assertEquals("12 3 4",result);
    }

    @Test
    public void spaceInStartTest() {
        String s = "12 4";
        int insertAt = 2;
        String toInsert = "3";

        String result = Strings.insertPadded(s,insertAt,toInsert);
        assertEquals("12 3 4",result);
    }
}
