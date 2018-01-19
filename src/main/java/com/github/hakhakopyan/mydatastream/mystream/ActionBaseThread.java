package com.github.hakhakopyan.mydatastream.mystream;

import main.java.read_file.FileReaderGivable;
import main.java.write_to_file.FileWritable;

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

        while (myActionThreads.stream().filter(x->x.isAlive()).count() > 0) {
            try {
                Thread.currentThread().wait(100);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
