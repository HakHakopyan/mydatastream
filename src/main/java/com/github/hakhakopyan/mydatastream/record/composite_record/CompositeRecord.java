package com.github.hakhakopyan.mydatastream.record.composite_record;

import com.github.hakhakopyan.mydatastream.readfile.FileReadable;
import com.github.hakhakopyan.mydatastream.record.AbstractBaseRecord;
import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.item.Itemable;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;
import com.github.hakhakopyan.mydatastream.record.simple_record.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositeRecord extends AbstractBaseRecord implements CompositeRecordable, FileReadable{

    List<Recordable> myNodes = new ArrayList<>();

    public CompositeRecord(String name) {
        super(name);
    }

    public void setRecord(Recordable newRecord) {
        myNodes.add(newRecord);
    }

    @Override
    public synchronized CompositeRecordable getCompositeRecord() {
        List<CompositeRecord> compositeRecordsList = myNodes.stream()
                .filter(x->!x.isSimpleRecord())
                .map(x->(CompositeRecord)x)
                .collect(Collectors.toList());

        if (myNodes.size() == 0 || compositeRecordsList.size() == 0)
            return new EmptyCompositeRecord();
        CompositeRecordable compositeRecord = compositeRecordsList.get(0);
        myNodes.remove(compositeRecord);
        return compositeRecord;
    }

    @Override
    public Stream<SimpleRecordable> getSimpleRecordsStream() {
        try {
            return this.myNodes.stream()
                    .filter(x -> x.isSimpleRecord())
                    .map(x -> (SimpleRecordable) x);
        } catch (ClassCastException ex) {
            System.out.println("We finde Exception " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean isSimpleRecord() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Itemable getItem(String itemName) {
        List<Itemable> items = getSimpleRecordsStream()
                .filter(x->x.getName().toLowerCase().equals(itemName.toLowerCase())).map(x->x.getItem())
                .collect(Collectors.toList());

        if (items.size() > 0) {
            return items.get(0);
        }

        return MyItem.getEmptyItem();
    }

    @Override
    public SimpleRecordable getSimpleRecord(String recordName) {
        List<SimpleRecordable> simpleRecords = getSimpleRecordsStream()
                .filter(x->x.getName().toLowerCase().equals(recordName.toLowerCase()))
                .collect(Collectors.toList());
        if (simpleRecords.size() > 0)
            return simpleRecords.get(0);
        return (SimpleRecordable) new EmptySimpleRecord();
    }

    @Override
    public CompositeRecordable getCompositeRecord(String recordName) {
        List<CompositeRecord> compositeRecords = this.myNodes.stream()
                .filter(x->!x.isSimpleRecord())
                .filter(x->x.getName().toLowerCase().equals(recordName.toLowerCase()))
                .map(x->(CompositeRecord) x)
                .collect(Collectors.toList());

        if (compositeRecords.size() > 0)
            return compositeRecords.get(0);

        return (CompositeRecord) new EmptyCompositeRecord();
    }
}
