package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;
import com.github.hakhakopyan.mydatastream.record.Recordable;


import java.util.function.Predicate;

public class Filter implements Actionable {
    Predicate<com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable> MyFilter;

    public Filter(Predicate<com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable> myFilter) {
        MyFilter = myFilter;
    }

    @Override
    public CompositeRecordable action(CompositeRecordable record) {
        if (MyFilter.test(record))
            return record;
        return new EmptyCompositeRecord();
    }
}
