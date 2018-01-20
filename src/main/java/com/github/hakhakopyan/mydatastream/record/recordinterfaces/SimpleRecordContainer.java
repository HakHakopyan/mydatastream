package com.github.hakhakopyan.mydatastream.record.recordinterfaces;

import com.github.hakhakopyan.mydatastream.record.SimpleRecord;

import java.util.stream.Stream;

public interface SimpleRecordContainer {
    public SimpleRecordContainer GetRecordContainSimpleNodes();
    public Stream<SimpleRecord> getSimpleRecordsStream();
}
