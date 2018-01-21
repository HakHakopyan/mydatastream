package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.File;
import java.io.IOException;

public abstract class AbstrFileReader implements FileReadable {
    private FileReadable myRecords;
    public AbstrFileReader(String fileName) throws IOException {
        File file = new File(fileName);
        this.myRecords = readFile(file);
    }

    @Override
    public CompositeRecordable getCompositeRecord() {
        synchronized (myRecords) {
            return this.myRecords.getCompositeRecord();
        }
    }

    abstract FileReadable readFile(File file);

    @Override
    public boolean isEmpty() {
        return this.myRecords.isEmpty();
    }
}
