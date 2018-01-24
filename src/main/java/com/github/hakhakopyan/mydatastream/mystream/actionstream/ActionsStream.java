package com.github.hakhakopyan.mydatastream.mystream.actionstream;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.Actions.Changer;
import com.github.hakhakopyan.mydatastream.Actions.Filter;
import com.github.hakhakopyan.mydatastream.Actions.Formater;
import com.github.hakhakopyan.mydatastream.mystream.actionthread.ActionBaseThread;
import com.github.hakhakopyan.mydatastream.readfile.ReadBaseThread;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ActionsStream {
    List<Actionable> myActions = new ArrayList<>();

    int threadCount = 1;

    String[] myFilePathes;

    public ActionsStream(String[] filePathes) {
        this.myFilePathes = filePathes;
    }

    public ActionsStream paralelize() {
        // уменьшаем на 1 так как у нас есть работающий поток для чтения записей из файла
        int procCount = Runtime.getRuntime().availableProcessors() - 1;

        return paralelize(procCount > 0 ? procCount : 1);
    }

    public ActionsStream paralelize(int threadCount) {
        this.threadCount = threadCount;

        return this;
    }

    public ActionsStream filter(Predicate<CompositeRecordable> condition) {
        myActions.add(new Filter(condition));

        return this;
    }

    public ActionsStream format(Predicate<Formatable> formater) {
        myActions.add(new Formater(formater));

        return this;
    }

    public ActionsStream change(UnaryOperator<CompositeRecordable> unaryOperator) {
        myActions.add(new Changer(unaryOperator));

        return this;
    }

    public void collect(FileType fileType) throws IOException {
        FileWritable fileWriter = fileType.getFileWriter();
        BlockingQueue<CompositeRecordable> blockingQueue = new LinkedBlockingDeque<>(100);
        // Запуск потока для чтения из файлов
        Thread baseWriter = new Thread(
                new ReadBaseThread(blockingQueue, this.myFilePathes));
        // Запуск основного потока для прогонки записей и записи в файл
        ActionBaseThread baseAction = new ActionBaseThread(myActions, blockingQueue, fileWriter,
                            threadCount, "ActionBaseStream");

        baseWriter.start();
        baseAction.start();
        try {
            baseWriter.join();
            while (!blockingQueue.isEmpty()) {
                Thread.sleep(100);
            }
            baseAction.stopThreads();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

       fileWriter.closeFile();
    }
}
