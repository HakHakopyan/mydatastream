package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.EmptyRecord;
import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;


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
