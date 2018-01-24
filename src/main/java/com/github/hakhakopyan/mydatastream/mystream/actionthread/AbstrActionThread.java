package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public abstract class AbstrActionThread extends Thread {
    List<Actionable> myActions;
    BlockingQueue<CompositeRecordable> myReadStream;
    FileWritable myWriter;

    public AbstrActionThread(List<Actionable> actions,
                             BlockingQueue<CompositeRecordable> readStream,
                             FileWritable writer,
                             String threadName) {
        super(threadName);
        this.myActions = actions;
        this.myReadStream = readStream;
        myWriter = writer;
    }
}
