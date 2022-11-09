package com.todotxt.todotxttouch.util;

import com.todotxt.todotxttouch.TodoException;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

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

    @Test
    public void joinNullCollection() {
        Collection<String> collection = null;
        assertEquals(Util.join(null, " "), "");
    }// Collection<String> collection = new HashSet<>();

    @Test
    public void joinStringsCollection() {
        Collection<String> collection = new HashSet<>();
        collection.add("a"); collection.add("b");
        assertEquals(Util.join(collection, " "), "a b");
    }

    @Test
    public void splitEmptyStringTest() {
        assertEquals(Util.split("", " "), Arrays.asList());
    }

    @Test
    public void splitStringTest() {
        assertEquals(Util.split("a b c", " "), Arrays.asList("a", "b", "c"));
    }

    @Test(expected = TodoException.class)
    public void createParentDirectoryNullDest() {
        Util.createParentDirectory(null);
    }

    @Test
    public void createParentDirectory() {
        File dest = new File("./dest/a");
        Util.createParentDirectory(dest);
    }

    @Test
    public void prependStringListTest() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("a"); strings.add("b");
        Util.prependString(strings, "-");
        assertEquals(strings.get(0), "-a");
        assertEquals(strings.get(1), "-b");
    }

    @Test(expected = TodoException.class)
    public void copyUnknownFileTest() {
        File unknown = new File("./unknown");
        File newFile = new File("./new");
        Util.copyFile(unknown, newFile, false);
    }

    @Test
    public void copyFileTest() {
        File origFile = new File("./orig");
        File newFile = new File("./new");
        try {
            origFile.createNewFile();
            InputStream is =  new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));;
            Util.writeFile(is, origFile);
            Util.copyFile(origFile, newFile, false);
            assertTrue(newFile.exists());
            BufferedReader br = new BufferedReader(new FileReader(newFile));
            assertEquals(br.readLine(), "test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        origFile.delete();
        newFile.delete();
    }

    @Test
    public void copyFileTestOverwrite() {
        File origFile = new File("./orig");
        File newFile = new File("./new");
        try {
            origFile.createNewFile();
            InputStream is =  new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8));
            Util.writeFile(is, origFile);
            newFile.createNewFile();
            is =  new ByteArrayInputStream("123".getBytes(StandardCharsets.UTF_8));;
            Util.writeFile(is, newFile);
            Util.copyFile(origFile, newFile, true);
            assertTrue(newFile.exists());
            assertEquals(Util.readStream(Files.newInputStream(newFile.toPath())).trim(), "test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        origFile.delete();
        newFile.delete();
    }

    @Test(expected = TodoException.class)
    public void renameUnknownFileTest() {
        File unknown = new File("./unknown");
        File newFile = new File("./new");
        Util.renameFile(unknown, newFile, false);
    }

    @Test
    public void renameFileTest() {
        File origFile = new File("./orig");
        File newFile = new File("./new");
        try {
            origFile.createNewFile();
            Util.renameFile(origFile, newFile, false);
            assertTrue(newFile.exists());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        origFile.delete();
        newFile.delete();
    }

    @Test
    public void renameFileTestOverwrite() {
        File origFile = new File("./orig");
        File newFile = new File("./new");
        try {
            origFile.createNewFile();
            newFile.createNewFile();
            Util.renameFile(origFile, newFile, true);
            assertTrue(newFile.exists());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        origFile.delete();
        newFile.delete();
    }
}
