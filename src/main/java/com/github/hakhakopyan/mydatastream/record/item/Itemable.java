package com.github.hakhakopyan.mydatastream.record.item;

public interface Itemable {
    String getValue();

    void setValue(String value);

    ItemType getType();

    void setType(ItemType type);
}
