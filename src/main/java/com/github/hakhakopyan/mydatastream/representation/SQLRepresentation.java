package com.github.hakhakopyan.mydatastream.representation;

import com.github.hakhakopyan.mydatastream.record.item.ItemType;
import com.github.hakhakopyan.mydatastream.record.recordinterfaces.SimpleRecordContainer;

import java.util.stream.Stream;

public class SQLRepresentation {// implements Representable
    //@Override
    public static Stream<String> GetRepresentation(SimpleRecordContainer baseRecord) {
        SimpleRecordContainer record = baseRecord.GetRecordContainSimpleNodes();
        Stream<String> recordStream = record.getSimpleRecordsStream()
                .map(r ->
                "'" + r.getName() + "', "
                + ((r.getItem().getType() == ItemType.TEXT) ? "'" + r.getItem().getValue() + "'" : "NULL") + ", "
                + ((r.getItem().getType() == ItemType.DATE) ? r.getItem().getValue() : "NULL")
                );

        return recordStream;
    }
}
