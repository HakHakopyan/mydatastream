package com.github.hakhakopyan.mydatastream.readfile;

import main.java.record.Record;
import main.java.record.Recordable;

import java.io.File;
import java.io.IOException;

public abstract class AbstrFileReader implements FileReadable {
    private Recordable myRecords;
    public AbstrFileReader(String fileName) throws IOException {
        File file = new File(fileName);
        this.myRecords = readFile(file);
    }

    @Override
    public Recordable getRecord() {
        synchronized (myRecords) {
            return ((Record) this.myRecords).getNode();
        }
    }

    abstract Recordable readFile(File file);

    @Override
    public boolean isEmpty() {
        return this.myRecords.IsEmptyRecord();
    }
}
