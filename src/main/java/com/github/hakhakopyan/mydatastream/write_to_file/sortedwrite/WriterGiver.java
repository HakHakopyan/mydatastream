package com.github.hakhakopyan.mydatastream.write_to_file.sortedwrite;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriterGiver implements WriterGivable {
    FileType myFileWriterType;
    Map<String, FileWritable> myRecords = new HashMap<>();
    FileWritable mySQLWriter;

    public WriterGiver(FileType fileType) throws IOException {
        this.myFileWriterType = myFileWriterType;
        if (fileType == FileType.SQL) {
            mySQLWriter = fileType.getFileWriter();
        }
    }

    /**
     * Возвращает "Пишущего в файл" для записи
     * Для SQL конечный пишушщий должен быть один
     * Для XML под каждый тип Записи пишущий должен быть уникаьным, так как запись идет в файл под данный тип Записи
     * @param record
     * @return
     * @throws IOException
     */
    @Override
    public FileWritable getWriter(CompositeRecordable record) throws IOException {
        if (!myRecords.containsKey(record.getName())) {
            switch (this.myFileWriterType) {
                case SQL:
                    myRecords.put(record.getName(), new NarrowSortedWriter(this.mySQLWriter, record.getName()));
            }
        }
        return myRecords.get(record.getName());
    }
}
