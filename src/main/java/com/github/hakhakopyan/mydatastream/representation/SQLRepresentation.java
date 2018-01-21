package com.github.hakhakopyan.mydatastream.representation;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;

import java.util.stream.Stream;

public class SQLRepresentation {// implements Representable
    //@Override
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
