package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActionBaseThread extends AbstrActionThread {
    int myThreadCount;
    List<Thread> myActionThreads = new ArrayList<>();
    private final ExecutorService pool;

    public ActionBaseThread(List<Actionable> actions, BlockingQueue<CompositeRecordable> readStream,
                            FileWritable writer, int threadCount, String threadName) {
        super(actions, readStream, writer, threadName);
        this.myThreadCount = threadCount;
        pool = Executors.newFixedThreadPool(threadCount);
    }

    public void stopThreads() {
        //pool.shutdown();
        pool.shutdownNow().stream().map(t->(ActionThread) t).forEach(t->t.stopRun());
    }

    @Override
    public void run() {

        for (int i = 1; i <= myThreadCount; i++) {
            pool.execute(new ActionThread(myActions, this.myReadStream, this.myWriter, "ActionStream_" + i));
        }
         /*
        for (int i = 1; i <= myThreadCount; i++) {
            myActionThreads.add(
                    new ActionThread(myActions, this.myReadStream, this.myWriter, "ActionStream_" + i));
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
        */
    }
}
