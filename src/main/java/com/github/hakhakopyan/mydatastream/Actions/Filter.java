package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;

import java.util.function.Predicate;

public class Filter<C extends CompositeRecordable&Formatable> implements Actionable<C> {
    Predicate<C> MyFormater;

    public Filter(Predicate<C> formater) {
        this.MyFormater = formater;
    }

    @Override
    public CompositeRecordable action(C record) {
        if (MyFormater.test(record))
            return record;
        return new EmptyCompositeRecord();
    }
}
