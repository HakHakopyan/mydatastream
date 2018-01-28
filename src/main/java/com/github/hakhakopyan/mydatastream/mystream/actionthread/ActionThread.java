package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGivable;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Реализует поток выполняющий прогонку записей через настроенные действия и запись в файл
 */
public class ActionThread extends AbstrActionThread {

    public ActionThread(List<Actionable> actions,
                        BlockingQueue<CompositeRecordable> readStream,
                        WriterGivable writerGiver,
                        String threadName) {
        super(actions, readStream, writerGiver, threadName);
    }

    private boolean isStop = false;

    /**
     * устанавливает {@link ActionThread#isStop} значение true
     * и фактически остановку выполнения потока
     */
    public void stopRun() {
        isStop = true;
    }

    /**
     * Запуск циклического выполнения прогонки записи через заданные действия и запись в файл
     */
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
                    synchronized (myWriterGiver) {
                        try {
                            myWriterGiver.getWriter(record).write(record);
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
