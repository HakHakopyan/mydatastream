package com.github.hakhakopyan.mydatastream.write_to_file.sortedwrite;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class SortedWriteToFile implements FileWritable {
    FileWritable myFileWriter;
    Map<String, FileWritable> myRecords = new HashMap<>();

    public SortedWriteToFile(FileWritable fileWriter) {
        this.myFileWriter = fileWriter;

    }

    @Override
    public void write(CompositeRecordable record) throws IOException {
        if (myRecords.containsKey(record.getName())) {
            myRecords.get(record.getName()).write(record);
        } else {
            myRecords.put(record.getName(), this.myFileWriter);
        }
    }

    @Override
    public void closeFile() throws IOException {

    }
}
