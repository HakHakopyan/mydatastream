package com.github.hakhakopyan.mydatastream.record.simple_record;

import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.item.Itemable;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;

/**
 * Interface for SimpleRecord which contain instance of {@link MyItem}, and can give it
 */
public interface SimpleRecordable extends Recordable {
    /**
     * return instance of {@link MyItem} that is contained in instance of {@link SimpleRecord}
     * @return instance of {@link MyItem}
     */
    Itemable getItem();
}
