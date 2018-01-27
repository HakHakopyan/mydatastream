package com.github.hakhakopyan.mydatastream.representation;

import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.simple_record.SimpleRecordable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class XMLRepresentation{

    public static List<String> GetRepresentation(CompositeRecordable baseRecord, int level) {
        List<String> retList = new ArrayList<>();
        retList.add(getSpaces(level++) + "<" + baseRecord.getName() + ">" + "\n");
        final int innerLevel = level + 1;
        for (Recordable r: baseRecord) {
            if (r.isSimpleRecord()) {
                retList.add(getSpaces(innerLevel) + "<" + r.getName() + ">"
                        + ((SimpleRecordable) r).getItem().getValue() + "</" + r.getName() + ">" + "\n");
            } else
                retList.containsAll(GetRepresentation((CompositeRecordable) r, innerLevel));
        }
        retList.add(getSpaces(level) + "</" + baseRecord.getName() + ">" + "\n");

        return retList;
    }

    private static String getSpaces(int level) {
        String spaces = "";
        for (int i=0; i < level; i++) {
            spaces += "  ";
        }
        return spaces;
    }
}
