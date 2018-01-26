package com.github.hakhakopyan.mydatastream.write_to_file.sortedwrite;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * stores and sends to write only one kind of record
 */
public class NarrowSortedWriter implements FileWritable {
    FileWritable myFileWriter;
    String myRecordsName = "";
    SortedMap<Integer, CompositeRecordable> myRecords = new TreeMap<>();
    int lastWritedRecordIndex = -1;

    public NarrowSortedWriter(FileWritable fileWriter, String recordsName) {
        this.myFileWriter = fileWriter;
        this.myRecordsName = recordsName;
    }

    @Override
    public void write(CompositeRecordable record) throws IOException {
        if (!myRecordsName.equals(record.getName()))
            return;
        if (record.getIndex() - lastWritedRecordIndex == 1) {
            lastWritedRecordIndex++;
            this.myFileWriter.write(record);
            if (!this.myRecords.isEmpty())
                while (this.myRecords.firstKey() - lastWritedRecordIndex == 1) {
                    this.myFileWriter.write(this.myRecords.remove(++lastWritedRecordIndex));
                }
        } else {
            myRecords.put(record.getIndex(), record);
        }

    }

    @Override
    public void closeFile() throws IOException {

    }
}
