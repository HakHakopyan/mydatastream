package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGivable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ActionBaseThread extends AbstrActionThread {
    int myThreadCount;
    List<Thread> myActionThreads = new ArrayList<>();
    private final ExecutorService pool;
    final BlockingQueue<Runnable> queue;

    public ActionBaseThread(List<Actionable> actions, BlockingQueue<CompositeRecordable> readStream,
                            WriterGivable writerGiver, int threadCount, String threadName) {
        super(actions, readStream, writerGiver, threadName);
        this.myThreadCount = threadCount;
        //pool = Executors.newFixedThreadPool(threadCount);
        queue = new ArrayBlockingQueue<>(threadCount);
        pool = new ThreadPoolExecutor(threadCount, threadCount, 0L, TimeUnit.MILLISECONDS, queue);
    }

    /**
     * Остановка пула потоков
     */
    public void stopThreads() {
        pool.shutdown();
        pool.shutdownNow();
        //shutdownAndAwaitTermination(this.pool);
        /*
        pool.shutdownNow().stream().map(t->(ActionThread) t).forEach(t->t.stopRun());
        List<ActionThread> actionThreads = pool.shutdownNow().stream().map(t->(ActionThread) t).collect(Collectors.toList());
        for (int i = 0; i < actionThreads.size(); i++) {
            actionThreads.get(i).stopRun();
        }
        */
    }
    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Создаем пул потоков, который содержит потоки выполяющие прогонку записей и запись их в файл
     */
    @Override
    public void run() {

        for (int i = 1; i <= myThreadCount; i++) {
            pool.execute(new ActionThread(myActions, this.myReadStream, this.myWriterGiver,
                    "ActionStream_" + i));
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
