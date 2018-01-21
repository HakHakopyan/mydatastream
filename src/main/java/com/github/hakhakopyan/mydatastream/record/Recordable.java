package com.github.hakhakopyan.mydatastream.record;

import com.github.hakhakopyan.mydatastream.record.simple_record.*;
import com.github.hakhakopyan.mydatastream.record.composite_record.*;

/**
 * base Interface for {@link SimpleRecord} and {@link CompositeRecord}
 */
public interface Recordable {
    boolean isSimpleRecord();
    boolean isEmpty();
    String getName();
    void setName(String recordName);
}
