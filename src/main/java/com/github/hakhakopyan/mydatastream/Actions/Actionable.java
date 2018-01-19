package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.Recordable;

public interface Actionable {
    public Recordable action(Recordable record);
}
