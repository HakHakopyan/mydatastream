package com.github.hakhakopyan.mydatastream.mystream.actionstream;

import com.github.hakhakopyan.mydatastream.Actions.*;
import com.github.hakhakopyan.mydatastream.Actions.functional_interfaces.VoidReturnable;
import com.github.hakhakopyan.mydatastream.mystream.MyQueueForNotParallelize;
import com.github.hakhakopyan.mydatastream.mystream.actionthread.ActionBaseThread;
import com.github.hakhakopyan.mydatastream.readfile.FileReadable;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderFarm;
import com.github.hakhakopyan.mydatastream.readfile.ReadBaseThread;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;
import com.github.hakhakopyan.mydatastream.write_to_file.writer_giver.WriterGiver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ActionsStream {
    List<Actionable> myActions = new ArrayList<>();

    int threadCount = 0;

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
        myActions.add(new Filter(formater));

        return this;
    }

    public ActionsStream change(UnaryOperator<CompositeRecordable> unaryOperator) {
        myActions.add(new Changer(unaryOperator));

        return this;
    }

    public ActionsStream modify(VoidReturnable unaryOperator) {
        myActions.add(new Modifier(unaryOperator));

        return this;
    }

    public CompositeRecordable reduce(BinaryOperator<CompositeRecordable> binaryOperator) {
        Reducer myReducer = new Reducer(binaryOperator);
        this.myActions.add(myReducer);
        execute(FileType.NOT_WRITE);

        return myReducer.getResult();
    }

    public void collect(FileType fileType) throws IOException {
        execute(fileType);
    }

    public void execute(FileType fileType) {
        try {
            if (this.threadCount > 0) {
                // Выполение с потоками а асинхронным IO
                executeWithThreads(fileType);
            } else {
                // Выполнение без потоков
                executeWithOutThreads(fileType);
            }
        } catch (IOException ex) {
            // запись в лог
        }
    }

    public void executeWithOutThreads(FileType fileType) throws IOException {
        List<FileReadable> fileReaders = Arrays.stream(myFilePathes)
                .map(path-> FileReaderFarm.getFileReader(path))
                .collect(Collectors.toList());

        WriterGiver writerGiver = new WriterGiver(fileType);
        BlockingQueue<CompositeRecordable> operationsDoer = new MyQueueForNotParallelize<>(this.myActions, writerGiver);

        for (FileReadable fileReader: fileReaders) {
            fileReader.readFile(operationsDoer);
        }

        writerGiver.closeFile();


    }

    public void executeWithThreads(FileType fileType) throws IOException {
        BlockingQueue<CompositeRecordable> blockingQueue = new LinkedBlockingDeque<>(100);
        // Запуск потока для чтения из файлов
        Thread baseReader = new Thread(
                new ReadBaseThread(blockingQueue, this.myFilePathes));

        WriterGiver writerGiver = new WriterGiver(fileType);
        // Запуск основного потока для прогонки записей и записи в файл
        ActionBaseThread baseAction = new ActionBaseThread(myActions, blockingQueue, writerGiver,
                threadCount, "ActionBaseStream");

        baseReader.start();
        baseAction.start();
        try {
            baseReader.join();
            while (!blockingQueue.isEmpty()) {
                Thread.sleep(100);
            }
            baseAction.stopThreads();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }

        writerGiver.closeFile();
    }
}
