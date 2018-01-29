package com.github.hakhakopyan.mydatastream.write_to_file.writer_giver;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;
import com.github.hakhakopyan.mydatastream.write_to_file.sortedwrite.NarrowSortedWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * It implements the storage and provision, as necessary,
 * of specialized objects that write to the record presentation in the file
 */
public class WriterGiver implements WriterGivable {
    FileType myFileWriterType;
    Map<String, FileWritable> myWriters = new HashMap<>();
    FileWritable myWriter;

    public WriterGiver(FileType fileType) throws IOException {
        this.myFileWriterType = fileType;
        if (checkForOneWriter()) {
            myWriter = fileType.getFileWriter();
        }
    }

    /**
     * Иногда требуется записать все виды записей в один файл или набор файлов
     * Метод реализует проверку такого устловия
     * @return true если ны выходе должен находиться один "Пишущий"
     */
    private boolean checkForOneWriter() {
        if (this.myFileWriterType == FileType.SQL
                || this.myFileWriterType == FileType.TXT
                || this.myFileWriterType == FileType.NOT_WRITE) {
            return true;
        }
        return false;
    }

    /**
     * Возвращает "Пишущего в файл" для записи
     * Для SQL конечный пишушщий должен быть один
     * Для XML под каждый тип Записи пишущий должен быть уникаьным, так как запись идет в файл под данный тип Записи
     * @param record Содержит запись, для которой нужно получить специализированного "Пишущего" в файл
     * @return "Пишущег" в файл
     * @throws IOException если возникли проблемы с дейтсвительностью пути или записью в файл
     */
    @Override
    public FileWritable getWriter(CompositeRecordable record) throws IOException {
        if (!myWriters.containsKey(record.getName())) {
            switch (this.myFileWriterType) {
                case SQL:
                    myWriters.put(record.getName(), new NarrowSortedWriter(this.myWriter, record.getName()));
                    break;
                case TXT:
                    myWriters.put(record.getName(), new NarrowSortedWriter(this.myWriter, record.getName()));
                    break;
                case XML:
                    myWriters.put(record.getName(),
                            new NarrowSortedWriter(this.myFileWriterType.getFileWriter(), record.getName()));
                    break;
                case NOT_WRITE:
                    return this.myWriter;

            }
        }
        return myWriters.get(record.getName());
    }

    @Override
    public void closeFile() throws IOException {
        Set<String> keys = this.myWriters.keySet();
        if (checkForOneWriter())
            this.myWriter.closeFile();
        else
            for (String key:keys) {
            FileWritable fW = this.myWriters.get(key);
            fW.closeFile();
            }
    }
}
