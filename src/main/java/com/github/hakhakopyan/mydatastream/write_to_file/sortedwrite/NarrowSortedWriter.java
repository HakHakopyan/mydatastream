package com.github.hakhakopyan.mydatastream.write_to_file.sortedwrite;

import com.github.hakhakopyan.mydatastream.readfile.FileReadable;
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
    int lastWritedRecordIndex = FileReadable.FIRST_RECORD_INDEX - 1;

    /**
     *
     * @param fileWriter An object that writes a specific record representation to a file
     * @param recordsName contain name of records which we will skip for writing
     */
    public NarrowSortedWriter(FileWritable fileWriter, String recordsName) {
        this.myFileWriter = fileWriter;
        this.myRecordsName = recordsName;
    }

    /**
     * implements a sorted records write
     * @param record instance of record which representation needed to write
     * @throws IOException if with write in file had problems
     */
    @Override
    public synchronized void write(CompositeRecordable record) throws IOException {
        if (!myRecordsName.equals(record.getName()))
            return;
        if (record.getIndex() - lastWritedRecordIndex == 1) {
            lastWritedRecordIndex++;
            this.myFileWriter.write(record);
            if (!this.myRecords.isEmpty())
                while (this.myRecords.firstKey() - lastWritedRecordIndex == 1) {
                    this.myFileWriter.write(this.myRecords.remove(++lastWritedRecordIndex));
                    if (this.myRecords.isEmpty())
                        break;
                }
        } else {
            myRecords.put(record.getIndex(), record);
        }

    }

    @Override
    public void closeFile() throws IOException {
        this.myFileWriter.closeFile();
    }
}
