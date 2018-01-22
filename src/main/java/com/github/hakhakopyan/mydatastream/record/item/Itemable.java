package com.github.hakhakopyan.mydatastream.record.item;

import java.text.ParseException;

public interface Itemable {
    String getValue();

    void setValue(String value);

    ItemType getType();

    void setType(ItemType type) throws ParseException;
}
