package com.github.hakhakopyan.mydatastream.record.composite_record;

import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.item.*;
import com.github.hakhakopyan.mydatastream.record.item.Itemable;
import com.github.hakhakopyan.mydatastream.record.simple_record.*;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Interface for composite records (Record that contain other records)
 */
public interface CompositeRecordable extends Recordable, Iterable<Recordable> {
    /**
     * returns all instances of {@link SimpleRecord}, even those contained in {@link CompositeRecord}
     * @return stream of {@link SimpleRecord}
     * @see SimpleRecordable
     */
    Stream<SimpleRecordable> getSimpleRecordsStream();

    /**
     * return instance of {@link MyItem} that is contained in instance of {@link SimpleRecord}
     * @param itemName the name of {@link SimpleRecord} which contains required instance of {@link MyItem}
     * @return instance of {@link MyItem}
     */
    Itemable getItem(String itemName);

    /**
     * return instance of {@link SimpleRecord}
     * @param recordName the name of the instance of {@link SimpleRecord} which needed to return
     * @return if instance of {@link SimpleRecord} is exist than return it, otherwise return instance of {@link EmptySimpleRecord}
     */
    SimpleRecordable getSimpleRecord(String recordName);

    /**
     * return instance of {@link CompositeRecord}
     * @param recordName the name of the instance of {@link CompositeRecord} which needed to return
     * @return if instance of {@link CompositeRecord} is exist than return it, otherwise return instance of {@link EmptyCompositeRecord}
     */
    CompositeRecordable getCompositeRecord(String recordName);

    /**
     * set new instance of {@link SimpleRecordable} or {@link CompositeRecordable} into instance of {@link CompositeRecordable}
     * @param newRecord instance of new Record
     */
    void setRecord(Recordable newRecord);

    /**
     * set new instance of {@link SimpleRecordable} or {@link CompositeRecordable} into instance of {@link CompositeRecordable}
     * @param newRecord instance of new Record
     * @param previousRecordName The name of the record, after which needed to insert a new record
     */
    void setRecord(Recordable newRecord, String previousRecordName);

    /**
     * Returns the name of the parent record to which the current record is inserted
     * @return parent record name
     */
    String getMyParentRecordName();

    /**
     * get the ordinal number of the record that was assigned to him when reading
     * @return ordinal record number
     */
    int getIndex();

    /**
     * set the ordinal record number
     */
    void  setIndex(int index);
}
