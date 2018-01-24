package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ActionThread extends AbstrActionThread {

    public ActionThread(List<Actionable> actions,
                        BlockingQueue<CompositeRecordable> readStream,
                        FileWritable writer,
                        String threadName) {
        super(actions, readStream, writer, threadName);
    }

    private boolean isStop = false;

    public void stopRun() {
        isStop = true;
    }

    @Override
    public void run() {
        CompositeRecordable record;
        try {
            while (!isStop) {
                record = myReadStream.take();
                for (Actionable action : myActions) {
                    record = action.action(record);
                    if (record.isEmpty())
                        break;
                }

                if (!record.isEmpty()) {
                    synchronized (myWriter) {
                        try {
                            myWriter.write(Thread.currentThread().getName());
                            myWriter.write(record);
                        } catch (IOException ex) {
                            // вывести сообщение в пул
                        }
                    }
                }
            }
        } catch (InterruptedException ex) {
            // вывести сообщение в лог
        }
    }

}
