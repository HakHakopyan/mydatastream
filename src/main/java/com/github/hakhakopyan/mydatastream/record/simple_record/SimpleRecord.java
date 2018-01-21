package com.github.hakhakopyan.mydatastream.record.simple_record;

import com.github.hakhakopyan.mydatastream.record.AbstractBaseRecord;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;

public class SimpleRecord extends AbstractBaseRecord implements SimpleRecordable {

    MyItem myItem;

    public SimpleRecord(String name, MyItem item) {
        super(name);
        this.myItem = new MyItem(item);
    }

    public SimpleRecord(String name, String value) {
        super(name);
        this.myItem = new MyItem(value, ItemType.TEXT);
    }

    @Override
    public MyItem getItem() {
        return this.myItem;
    }

    @Override
    public boolean isSimpleRecord() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
