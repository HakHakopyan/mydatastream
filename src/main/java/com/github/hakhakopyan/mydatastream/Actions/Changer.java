package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.composite_record.*;

import java.util.function.UnaryOperator;

public class Changer implements Actionable {
    UnaryOperator<CompositeRecordable> myChanger;

    public Changer(UnaryOperator<CompositeRecordable> myChanger) {
        this.myChanger = myChanger;
    }

    @Override
    public CompositeRecordable action(CompositeRecordable record) {

        return (CompositeRecordable) myChanger.apply(record);
    }
}
