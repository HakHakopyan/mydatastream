package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.EmptyRecord;
import com.github.hakhakopyan.mydatastream.record.Recordable;

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
