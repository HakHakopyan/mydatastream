package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGivable;
import com.github.hakhakopyan.mydatastream.write_to_file.*;

import java.util.ArrayList;
import java.util.List;

public class ActionBaseThread extends AbstrActionThread {
    int myThreadCount;
    List<Thread> myActionThreads = new ArrayList<>();

    public ActionBaseThread(List<Actionable> actions, FileReaderGivable reader, FileWritable writer, int threadCount) {
        super(actions, reader, writer);
        this.myThreadCount = threadCount;
    }

    @Override
    public void run() {
        for (int i = 1; i <= myThreadCount; i++) {
            myActionThreads.add(new Thread(
                    new ActionThread(myActions, this.myReader, this.myWriter), "ActionStream_" + i));
        }
        for (Thread thread: myActionThreads) {
            thread.start();
        }

        for (Thread thread: myActionThreads) {
            try {
                if (thread.isAlive())
                    thread.join();
            } catch(InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
