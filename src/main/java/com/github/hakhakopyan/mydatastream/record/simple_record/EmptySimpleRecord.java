package com.github.hakhakopyan.mydatastream.record.simple_record;

import com.github.hakhakopyan.mydatastream.record.composite_record.EmptyCompositeRecord;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;

public class EmptySimpleRecord extends SimpleRecord {

    public EmptySimpleRecord() {
        super(EMPTY_RECORD_NAME, MyItem.getEmptyItem());
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * Nothing, because our EmptyRecord already have name - {@link EmptySimpleRecord#EMPTY_RECORD_NAME}
     * @param recordName never mind
     */
    @Override
    public void setName(String recordName) {

    }
}
