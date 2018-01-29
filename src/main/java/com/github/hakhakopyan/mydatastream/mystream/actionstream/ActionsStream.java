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

/**
 * Реализует настройку действий для обработки Записей по модели паттерна Builder
 * Конечной фазой для запуска работы является вызов методов {@link ActionsStream#reduce(BinaryOperator)}
 * and {@link ActionsStream#collect(FileType)}
 */
public class ActionsStream {
    /**
     * Содержит последовательность действий, которые настроил пользователь, применяемых к экземпляру {@link CompositeRecord}
     */
    List<Actionable> myActions = new ArrayList<>();

    /**
     * Параметр для настройки асинхкронного выполнения IO и количества работающих потоков
     */
    int threadCount = 0;

    /**
     * Содержит пути файлов с которых будет осуществляться чтение записей
     */
    String[] myFilePathes;

    /**
     * Инициализируем {@link ActionsStream#myFilePathes}
     * @param filePathes Contains filenames from which we will read records
     */
    public ActionsStream(String[] filePathes) {
        this.myFilePathes = filePathes;
    }

    /**
     * Распаралелить работу системы и включить асинхронное чтние IO
     * количество потоков равняется колиеству процессоров ЭВМ минус 1 для потока чтения
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream paralelize() {
        // уменьшаем на 1 так как у нас есть работающий поток для чтения записей из файла
        int procCount = Runtime.getRuntime().availableProcessors() - 1;

        return paralelize(procCount > 0 ? procCount : 1);
    }

    /**
     * Распаралелить работу системы и включить асинхронное чтние IO
     * количество потоков равняется колиеству задаваемых потоков
     * @param threadCount содержит количество работающих потоков, поток чтения не учитывается
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream paralelize(int threadCount) {
        this.threadCount = threadCount;

        return this;
    }

    /**
     * Принимаем лямбду выполняющую проверку записи на определенное условие
     * и сохранение ее в instance of {@link Filter}
     * Является промежуточной операцией
     * @param condition содержит лямбду, выполняющую проверку записи на опреденное условие
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream filter(Predicate<CompositeRecordable> condition) {
        myActions.add(new Filter(condition));

        return this;
    }

    /**
     * Принимает лямбду для форматирования поля с датой у записи
     * и сохранение ее в instance of {@link Filter}
     * Является промежуточной операцией
     * @param formater Содержит лямбду, форматирующую значение поля с датой
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream format(Predicate<Formatable> formater) {
        myActions.add(new Filter(formater));

        return this;
    }

    /**
     * Принимаем лямбду изменяющую запись, реализована на интерфейсе {@link UnaryOperator}
     * и сохранение ее в instance of {@link Changer}
     * Является промежуточной операцией
     * @param unaryOperator содержит лямбду для изменения записи
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream change(UnaryOperator<CompositeRecordable> unaryOperator) {
        myActions.add(new Changer(unaryOperator));

        return this;
    }

    /**
     * Принимаем лямбду изменяющую запись, реализована на интерфейсе {@link VoidReturnable}
     * и сохранение ее в instance of {@link Modifier}
     * Является промежуточной операцией
     * @param unaryOperator содержит лямбду для изменения записи
     * @return instance of {@link ActionsStream} in which the method was called
     */
    public ActionsStream modify(VoidReturnable unaryOperator) {
        myActions.add(new Modifier(unaryOperator));

        return this;
    }

    /**
     * Принимает бинарную лямбду, выполняющую операция над двумя записями
     * сохраненяет ее в instance of {@link Reducer}
     * Запускает выполнение {@link ActionsStream#execute(FileType)} без вывода в файл
     * получает результат у созданной до этого {@link Reducer} и возвращает его
     * Является теминальной операцией
     * @param binaryOperator содержит бинарный метод выполнения дейтсвия над двумя записями
     * @return instance of {@link CompositeRecord}
     */
    public CompositeRecordable reduce(BinaryOperator<CompositeRecordable> binaryOperator) {
        Reducer myReducer = new Reducer(binaryOperator);
        this.myActions.add(myReducer);
        execute(FileType.NOT_WRITE);

        return myReducer.getResult();
    }

    /**
     * Принимает тип представления записи на выходе
     * @param fileType содержит тип файла или тип представления на выходе
     * @throws IOException if we have troubles with file IO
     */
    public void collect(FileType fileType) throws IOException {
        execute(fileType);
    }

    /**
     * запускает выполнение {@link ActionsStream#executeWithOutThreads(FileType)}
     * если пользователь не настроил паралельное выполнение через {@link ActionsStream#paralelize()}
     * запускает выполнение {@link ActionsStream#executeWithThreads(FileType)}
     * если пользователь настроил выполнение через потоки
     * @param fileType Содержит тип представления Записи на выходе
     */
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

    /**
     * Выполнение в основном потоке
     * @param fileType тип представления записи на выходе
     * @throws IOException if we have troubles with file IO
     */
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

    /**
     * Выполнение через асинхронное IO и паралельную обработку записей
     * @param fileType тип представления записи на выходе
     * @throws IOException if we have troubles with file IO
     */
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
