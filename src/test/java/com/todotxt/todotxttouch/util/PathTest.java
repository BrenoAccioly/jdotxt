package com.todotxt.todotxttouch.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathTest {

    @Test
    public void testFileName() {
        assertEquals(Path.fileName(""), "");
        assertEquals(Path.fileName("abc/cde"), "cde");
        assertEquals(Path.fileName("/abc/cde/"), "cde");
        assertEquals(Path.fileName("/abc/cde"), "cde");
    }

    @Test
    public void testParentPath() {
        assertEquals(Path.parentPath(""), "");
        assertEquals(Path.parentPath("/"), "");
        assertEquals(Path.parentPath("abc/cde"), "abc/");
        assertEquals(Path.parentPath("abc/cde/"), "abc/");
        assertEquals(Path.parentPath("/abc/cde/"), "/abc/");
    }
}
