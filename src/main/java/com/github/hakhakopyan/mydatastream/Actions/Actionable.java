package com.github.hakhakopyan.mydatastream.Actions;

import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.Formatable;

public interface Actionable <C extends CompositeRecordable&Formatable> {
    CompositeRecordable action(C record);
}
