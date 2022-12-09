package com.todotxt.todotxttouch.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TreeTest {

    Tree<String> tree = new Tree<>("a");

    @Before
    public void restart(){
        tree = new Tree<>("a");
    }

    @Test
    public void testTreeFromParent() {
        Tree<String> child = new Tree(tree, "b");
        assertEquals("a", child.getParent().getData());
        assertEquals("b", child.getData());
    }

    @Test
    public void testContains() {
        Tree<String> child = new Tree(tree, "b");
        tree.addChild(child);

        assertTrue(tree.contains(child));
        assertFalse(child.contains(tree));

    }

    @Test
    public void testAddChild() {
        assertFalse(tree.isLoaded());
        tree.setLoaded();
        assertTrue(tree.isLoaded());

        Tree<String> toAdd = new Tree<>("b");

        Tree<String> child = tree.addChild(toAdd);
        assertEquals("b", child.getData());

        assertTrue(tree.contains(toAdd));
        assertEquals(toAdd.getParent().getData(), "a");
        assertTrue(tree.contains("b"));
        assertFalse(tree.contains("c"));
        assertEquals(tree.getChildren(), Arrays.asList(toAdd));


        tree.addChild("c");
        assertEquals(tree.getChild(0).getData(), "b");
        assertEquals(tree.getChild(1).getData(), "c");
    }

    @Test
    public void testAddChildToUnitializedTree() {
        Tree<String> toAdd = new Tree<>("b");
        tree.addChild(toAdd);
    }
}
