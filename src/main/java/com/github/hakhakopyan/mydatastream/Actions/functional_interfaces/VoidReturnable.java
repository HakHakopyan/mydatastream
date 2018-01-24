package com.github.hakhakopyan.mydatastream.Actions.functional_interfaces;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

public interface VoidReturnable {
    void execute(CompositeRecordable record);
}
