package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

public interface FileReadable {
    public CompositeRecordable getCompositeRecord();
    public boolean isEmpty();
}
