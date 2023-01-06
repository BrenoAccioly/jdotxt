package com.chschmid.jdotxt.gui.utils;

import javax.swing.*;
import java.io.*;

public class UtilGUI {
    public static Object cloneObject(Object original) {
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(original);
            ByteArrayInputStream copyByteStream = new ByteArrayInputStream(byteStream.toByteArray());
            ObjectInputStream copy = new ObjectInputStream(copyByteStream);
            return copy.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
