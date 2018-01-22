package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;


import java.util.function.Predicate;

public  class Filter implements Actionable  {
    Predicate<CompositeRecordable> MyFilter;

    public Filter(Predicate<CompositeRecordable> myFilter) {
        MyFilter = myFilter;
    }

    @Override
    public CompositeRecordable action(CompositeRecordable record) {
        if (MyFilter.test(record))
            return record;
        return new EmptyCompositeRecord();
    }
}
