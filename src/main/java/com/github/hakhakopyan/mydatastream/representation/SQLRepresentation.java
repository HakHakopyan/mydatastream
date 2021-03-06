package com.github.hakhakopyan.mydatastream.representation;

import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * SQL record represent
 */
public class SQLRepresentation {

    /**
     * This is a record view that is a parameter for the Insert method in the sql table
     * @param baseRecord record whose presentation we need to get
     * @return The stream of list of record representations that is a parameter for the Insert method in the sql table
     */
    public static Stream<String> GetRepresentation(CompositeRecordable baseRecord) {
        //CompositeRecordable record = baseRecord.GetRecordContainSimpleNodes();

        Stream<String> recordStream = baseRecord.getSimpleRecordsStream()
                .map(r ->
                "'" + r.getName() + "', "
                + ((r.getItem().getType() == ItemType.TEXT) ? "'" + r.getItem().getValue() + "'" : "NULL") + ", "
                + ((r.getItem().getType() == ItemType.DATE) ? r.getItem().getValue() : "NULL")
                );

        return recordStream;
    }
}
