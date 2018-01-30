package com.github.hakhakopyan.mydatastream.mystream;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGivable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @param <E> объект имплементящий интерфейс {@link CompositeRecordable}
 */
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

    /**
     * Переопределяем метод для выполненения всех действий по рогонке записи через Actions и запись в файл
     * @param record instance of {@link CompositeRecord}
     * @return true always
     */
    @Override
    public boolean add(E record) {
        //record = (CompositeRecordable) record;
        int reordIndex = record.getIndex();
        for (Actionable action : myActions) {
            try {
                record = (E) action.action(record);
            } catch (Exception ex) {
                System.out.println("Exception, when trying to perform an action("
                        + action.getClass().getSimpleName() + ")/n  " + ex.getMessage());
            }
            if (record.isEmpty()) {
                record.setIndex(reordIndex);
                break;
            }
        }
        try {
            myWriterGiver.getWriter(record).write(record);
        } catch (IOException ex) {
            // вывести сообщение в лог
        }
        return true;
    }
}
