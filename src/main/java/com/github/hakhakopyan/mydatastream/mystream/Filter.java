package com.github.hakhakopyan.mydatastream.mystream;

import main.java.record.EmptyRecord;
import main.java.record.Recordable;

import java.util.function.Predicate;

public class Filter implements Actionable {
    //Actionable myAction;
    Predicate<Recordable> MyFilter;

    public Filter(Predicate<Recordable> myFilter) {
        //this.myAction = myAction;
        MyFilter = myFilter;
    }

    public Recordable action(Recordable record) {
        if (MyFilter.test(record))
            return record;
        return new EmptyRecord();
    }
}
