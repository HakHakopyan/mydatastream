package com.github.hakhakopyan.mydatastream.record.simple_record;

import com.github.hakhakopyan.mydatastream.record.AbstractBaseRecord;
import com.github.hakhakopyan.mydatastream.record.item.Itemable;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;

/**
 * Запись, которая содержит только значение, не является компонентной
 */
public class SimpleRecord extends AbstractBaseRecord implements SimpleRecordable {

    Itemable myItem;

    public SimpleRecord(String name, Itemable item) {
        super(name);
        this.myItem = new MyItem(item);
    }

    public SimpleRecord(String name, String value) {
        super(name);
        this.myItem = new MyItem(value, ItemType.TEXT);
    }

    public SimpleRecord(SimpleRecordable simpleRecord) {
        super(simpleRecord.getName());
        this.myItem = new MyItem(simpleRecord.getItem());
    }

    @Override
    public Itemable getItem() {
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

    @Override
    public String toString() {
        return this.getName() + " - " + this.myItem;
    }
}
