package com.github.hakhakopyan.mydatastream.record;

import java.util.stream.Stream;

public interface SimpleRecordContainer {
    public SimpleRecordContainer GetRecordContainSimpleNodes();
    public Stream<SimpleRecord> getSimpleRecordsStream();
}
