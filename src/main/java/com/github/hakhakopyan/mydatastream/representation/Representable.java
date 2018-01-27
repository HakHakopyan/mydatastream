package com.github.hakhakopyan.mydatastream.representation;


import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.util.stream.Stream;

public interface Representable {
    Stream<String> GetRepresentation(CompositeRecordable baseRecord);
}
