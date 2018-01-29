package com.github.hakhakopyan.mydatastream.representation;


import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.util.stream.Stream;

/**
 * Interface for Represents records in other type
 */
@Deprecated
public interface Representable {
    /**
     * get Record represent
     * @param baseRecord comtais record, which represent we need
     * @return record represent on specific format
     */
    Stream<String> GetRepresentation(CompositeRecordable baseRecord);
}
