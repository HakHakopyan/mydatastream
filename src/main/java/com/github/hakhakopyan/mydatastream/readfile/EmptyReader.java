package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;

public class EmptyReader implements FileReadable {
    @Override
    public CompositeRecordable getCompositeRecord() {
        return new EmptyCompositeRecord();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
