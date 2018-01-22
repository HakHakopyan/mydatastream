package com.github.hakhakopyan.mydatastream.record.composite_record;

import com.github.hakhakopyan.mydatastream.record.item.ItemType;

public interface Formatable {
    boolean setItemFormat(String itemName, ItemType itemType);
}
