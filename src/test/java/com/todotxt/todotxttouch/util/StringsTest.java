package com.todotxt.todotxttouch.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StringsTest.InsertPaddedIfNeededTest.class,
        StringsTest.InsertPaddedTest.class,
        StringsTest.IsBlankTest.class
})
public class StringsTest {

    public static class InsertPaddedIfNeededTest {

        @Test
        public void stringAlreadyPresent() {
            // Given
            String s = "a b c";
            int insertAt = 0;
            String toInsert = "b";

            // When
            String result = Strings.insertPaddedIfNeeded(s, insertAt, toInsert);

            // Then
            assertEquals("a b c ", result);
        }

        @Test
        public void baseStringIsNull() {
            // Given
            String s = null;
            int insertAt = 0;
            String toInsert = "1";

            // When
            String result = Strings.insertPaddedIfNeeded(s, insertAt, toInsert);

            // Then
            assertEquals("1", result);
        }

        @Test
        public void baseStringIsEmpty() {
            // Given
            String s = "";
            int insertAt = 0;
            String toInsertEmpty = "";
            String toInsertNotEmpty = "1";

            // When
            String result1 = Strings.insertPaddedIfNeeded(s, insertAt, toInsertEmpty);
            String result2 = Strings.insertPaddedIfNeeded(s, insertAt, toInsertNotEmpty);

            // Then
            assertEquals("", result1);
            assertEquals("1", result2);
        }

        @Test
        public void spaceInInsertAtTest() {
            // Given
            String s = "12 4";
            int insertAt = 2;
            String toInsert = "3";

            // When
            String result = Strings.insertPaddedIfNeeded(s, insertAt, toInsert);

            // Then
            assertEquals("12 3 4", result);
        }

        @Test
        public void spaceInBothEndsTest() {
            // Given
            String s = "12 4";
            int insertAtLeft = 1, insertAtRight = 3;
            String toInsert = "3";

            // When
            String resultLeft = Strings.insertPaddedIfNeeded(s, insertAtLeft, toInsert);
            String resultRight = Strings.insertPaddedIfNeeded(s, insertAtRight, toInsert);

            // Then
            assertEquals("1 3 2 4", resultLeft);
            assertEquals("12 3 4", resultRight);
        }

        @Test
        public void noSpaceInBothEndsTest() {
            // Given
            String s = "124";
            int insertAt = 1;
            String toInsert = "3";

            // When
            String result = Strings.insertPaddedIfNeeded(s, insertAt, toInsert);

            // Then
            assertEquals("1 3 24", result);
        }

        @Test
        public void inBoundsInsertionPoint() {
            // Given
            String s = "124";
            int insertAtLowerBound = 0, insertAtUpperBound = 3;
            String toInsert = "3";

            // When
            String resultLowerBound = Strings.insertPaddedIfNeeded(s, insertAtLowerBound, toInsert);
            String resultUpperBound = Strings.insertPaddedIfNeeded(s, insertAtUpperBound, toInsert);

            // Then
            assertEquals("3 124", resultLowerBound);
            assertEquals("124 3 ", resultUpperBound);
        }

        // Throws an unexpected error
        @Test
        public void outBoundsInsertionPoint() {
            // Given
            String s = "124";
            int insertAtLeftBoundary = -1, insertAtRightBoundary = 4;

            String toInsert = "3";

            // When
            try {
                Strings.insertPaddedIfNeeded(s, insertAtLeftBoundary, toInsert);
                fail("No exception was thrown");
            } catch (IndexOutOfBoundsException ignore) {
            }

            try {
                Strings.insertPaddedIfNeeded(s, insertAtRightBoundary, toInsert);
                fail("No exception was thrown");
            } catch (IndexOutOfBoundsException ignore) {
            }

        }

        @Test
        public void stringToInsertIsNull() {
            // Given
            String s = "1";
            int insertAt = 1;
            String toInsert = null;

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1", result);
        }

        @Test
        public void stringToInsertIsEmpty() {
            // Given
            String sEmpty = "", sNotEmpty = "1";
            int insertAt = 1;
            String toInsert = "";

            // When
            String result1 = Strings.insertPadded(sEmpty, insertAt, toInsert);
            String result2 = Strings.insertPadded(sNotEmpty, insertAt, toInsert);

            // Then
            assertEquals("", result1);
            assertEquals("1", result2);
        }

        @Test
        public void stringToInsertIsNotEmpty() {
            // Given
            String s = "13";
            int insertAt = 1;
            String toInsert = "2";

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1 2 3", result);
        }
    }

    public static class InsertPaddedTest {
        @Test
        public void baseStringIsNull() {
            // Given
            String s = null;
            int insertAt = 0;
            String toInsert = "1";

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1", result);
        }

        @Test
        public void baseStringIsEmpty() {
            // Given
            String s = "";
            int insertAt = 0;
            String toInsertEmpty = "";
            String toInsertNotEmpty = "1";

            // When
            String result1 = Strings.insertPadded(s, insertAt, toInsertEmpty);
            String result2 = Strings.insertPadded(s, insertAt, toInsertNotEmpty);

            // Then
            assertEquals("", result1);
            assertEquals("1", result2);
        }

        @Test
        public void spaceInInsertAtTest() {
            // Given
            String s = "12 4";
            int insertAt = 2;
            String toInsert = "3";

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("12 3 4", result);
        }

        @Test
        public void spaceInBothEndsTest() {
            // Given
            String s = "12 4";
            int insertAtLeft = 1, insertAtRight = 3;
            String toInsert = "3";

            // When
            String resultLeft = Strings.insertPadded(s, insertAtLeft, toInsert);
            String resultRight = Strings.insertPadded(s, insertAtRight, toInsert);

            // Then
            assertEquals("1 3 2 4", resultLeft);
            assertEquals("12 3 4", resultRight);
        }

        @Test
        public void noSpaceInBothEndsTest() {
            // Given
            String s = "124";
            int insertAt = 1;
            String toInsert = "3";

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1 3 24", result);
        }

        @Test
        public void inBoundsInsertionPoint() {
            // Given
            String s = "124";
            int insertAtLowerBound = 0, insertAtUpperBound = 3;
            String toInsert = "3";

            // When
            String resultLowerBound = Strings.insertPadded(s, insertAtLowerBound, toInsert);
            String resultUpperBound = Strings.insertPadded(s, insertAtUpperBound, toInsert);

            // Then
            assertEquals("3 124", resultLowerBound);
            assertEquals("124 3 ", resultUpperBound);
        }

        // Throws an unexpected error
        @Test
        public void outBoundsInsertionPoint() {
            // Given
            String s = "124";
            int insertAtLeftBoundary = -1, insertAtRightBoundary = 4;

            String toInsert = "3";

            // When
            try {
                Strings.insertPadded(s, insertAtLeftBoundary, toInsert);
                fail("No exception was thrown");
            } catch (IndexOutOfBoundsException ignore) {
            }

            try {
                Strings.insertPadded(s, insertAtRightBoundary, toInsert);
                fail("No exception was thrown");
            } catch (IndexOutOfBoundsException ignore) {
            }

        }

        @Test
        public void stringToInsertIsNull() {
            // Given
            String s = "1";
            int insertAt = 1;
            String toInsert = null;

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1", result);
        }

        @Test
        public void stringToInsertIsEmpty() {
            // Given
            String sEmpty = "", sNotEmpty = "1";
            int insertAt = 1;
            String toInsert = "";

            // When
            String result1 = Strings.insertPadded(sEmpty, insertAt, toInsert);
            String result2 = Strings.insertPadded(sNotEmpty, insertAt, toInsert);

            // Then
            assertEquals("", result1);
            assertEquals("1", result2);
        }

        @Test
        public void stringToInsertIsNotEmpty() {
            // Given
            String s = "13";
            int insertAt = 1;
            String toInsert = "2";

            // When
            String result = Strings.insertPadded(s, insertAt, toInsert);

            // Then
            assertEquals("1 2 3", result);
        }
    }

    public static class IsBlankTest {

        @Test
        public void nullIsBlankTest () {
            // Given
            String s = null;

            // When
            boolean result = Strings.isBlank(s);

            // Then
            assertTrue(result);
        }

        @Test
        public void emptyIsBlankTest () {
            // Given
            String s = "";

            // When
            boolean result = Strings.isBlank(s);

            // Then
            assertTrue(result);
        }

        @Test
        public void whitespaceOnlyIsBlankTest () {
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
}
