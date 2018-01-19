package com.github.hakhakopyan.mydatastream.readfile;

import main.java.record.EmptyRecord;
import main.java.record.Recordable;

public class EmptyReader implements FileReadable {
    @Override
    public Recordable getRecord() {
        return new EmptyRecord();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
