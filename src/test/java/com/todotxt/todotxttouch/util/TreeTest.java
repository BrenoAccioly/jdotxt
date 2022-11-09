package com.todotxt.todotxttouch.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TreeTest {

    Tree<String> tree = new Tree<>("a");

    @Test
    public void testAddChild() {
        assertFalse(tree.isLoaded());
        tree.setLoaded();
        assertTrue(tree.isLoaded());

        Tree<String> toAdd = new Tree<>("b");

        tree.addChild(toAdd);

        assertTrue(tree.contains(toAdd));
        assertEquals(toAdd.getParent().getData(), "a");
        assertTrue(tree.contains("b"));
        assertFalse(tree.contains("c"));
        assertEquals(tree.getChildren(), Arrays.asList(toAdd));


        tree.addChild("c");
        assertEquals(tree.getChild(0).getData(), "b");
        assertEquals(tree.getChild(1).getData(), "c");
    }
}
