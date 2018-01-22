package com.github.hakhakopyan.mydatastream.record.item;

import java.text.ParseException;

public class MyItem implements Itemable {
    String value;
    ItemType type;

    public MyItem(String value, ItemType type) {
        this.value = value;
        this.type = type;
    }

    public MyItem(MyItem item) {
        this(item.getValue(), item.getType());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) throws ParseException {
        this.type = type;
        this.value = this.type.getRepresent(this.value);
    }

    public static MyItem getEmptyItem() {
        return new MyItem("", ItemType.NULL);
    }
}
