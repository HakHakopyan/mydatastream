package com.github.hakhakopyan.mydatastream.record;

import com.github.hakhakopyan.mydatastream.record.simple_record.*;
import com.github.hakhakopyan.mydatastream.record.composite_record.*;

/**
 * base Interface for {@link SimpleRecord} and {@link CompositeRecord}
 */
public interface Recordable {
    /**
     * Checks an object compound or single with one value
     * @return true if Simple
     */
    boolean isSimpleRecord();

    /**
     * The record is empty, not containing any specific value
     * @return true if empty
     */
    boolean isEmpty();

    /**
     * Return Record name
     * @return Record name
     */
    String getName();

    /**
     * Set Record name
     * @param recordName contains an installed name
     */
    void setName(String recordName);
}
