package com.github.hakhakopyan.mydatastream.Actions.functional_interfaces;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

/**
 * Functional interface for modification instance of {@link CompositeRecord}
 */
public interface VoidReturnable {
    /**
     * Method for modification record
     * @param record contains instance of {@link CompositeRecord}, which needed to modify
     */
    void execute(CompositeRecordable record);
}
