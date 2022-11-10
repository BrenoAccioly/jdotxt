package com.todotxt.todotxttouch.task.sorter;

import com.chschmid.jdotxt.Jdotxt;
import com.todotxt.todotxttouch.task.LocalFileTaskRepository;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class LocalFileTaskRepositoryTest {
    @Test
    public void initTest() throws IOException {
        LocalFileTaskRepository localFileTaskRepository = new LocalFileTaskRepository();
        File file = new File(Jdotxt.DEFAULT_DIR + File.separator + "todo.txt");
        if (file.exists()) file.delete();
        localFileTaskRepository.init();

    }

    @Test
    public void initFilesTest() {
        File file = new File(Jdotxt.DEFAULT_DIR + File.separator + "todo.txt");
        if (file.exists()) file.delete();
        LocalFileTaskRepository.initFiles();
    }
}
