package com.github.hakhakopyan.mydatastream.mystream;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGivable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class MyQueueForNotParallelize<E extends CompositeRecordable> extends LinkedBlockingQueue<E> {
    List<Actionable> myActions;
    WriterGivable myWriterGiver;

    /**
     * Creates a {@code LinkedBlockingQueue} with the given (fixed) capacity.
     *
     * @throws IllegalArgumentException if {@code capacity} is not greater
     *                                  than zero
     */
    public MyQueueForNotParallelize(List<Actionable> actions, WriterGivable writerGiver) {
        super(1);
        this.myActions = actions;
        this.myWriterGiver = writerGiver;
    }

    @Override
    public boolean add(E record) {
        //record = (CompositeRecordable) record;
        for (Actionable action : myActions) {
            record = (E) action.action(record);
            if (record.isEmpty())
                break;
        }
        if (!record.isEmpty()) {
            try {
                myWriterGiver.getWriter(record).write(record);
            } catch (IOException ex) {
                // вывести сообщение в пул
            }
        }
        return true;
    }
}
