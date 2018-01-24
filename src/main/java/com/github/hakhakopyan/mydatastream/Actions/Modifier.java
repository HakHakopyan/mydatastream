package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.Actions.functional_interfaces.VoidReturnable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

public class Modifier implements Actionable {
    VoidReturnable myModifier;

    public Modifier(VoidReturnable modifier) {
        this.myModifier = modifier;
    }

    @Override
    public synchronized CompositeRecordable action(CompositeRecordable record) {

        this.myModifier.execute(record);

        return record;
    }
}
