package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGivable;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Абстрактный класс, хранящий параметры, необходимые для работы потоков, выполняющих работу по прогонке записей
 * через настроенные действия и запись в файл заддного типа
 */
public abstract class AbstrActionThread extends Thread {
    /**
     * Список действий над записями
     */
    List<Actionable> myActions;
    /**
     * Чтение записей
     */
    BlockingQueue<CompositeRecordable> myReadStream;
    /**
     * Получение записывающего в файл для конкретной записи
     */
    WriterGivable myWriterGiver;

    /**
     * Инициализируем параметры
     * @param actions Список действий над записями
     * @param readStream Чтение записей
     * @param writerGiver Получение записывающего в файл для конкретной записи
     * @param threadName Имя потока
     */
    public AbstrActionThread(List<Actionable> actions,
                             BlockingQueue<CompositeRecordable> readStream,
                             WriterGivable writerGiver,
                             String threadName) {
        super(threadName);
        this.myActions = actions;
        this.myReadStream = readStream;
        this.myWriterGiver = writerGiver;
    }
}
