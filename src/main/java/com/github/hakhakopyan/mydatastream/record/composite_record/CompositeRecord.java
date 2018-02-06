package com.github.hakhakopyan.mydatastream.record.composite_record;

import com.github.hakhakopyan.mydatastream.readfile.FileReadable;
import com.github.hakhakopyan.mydatastream.record.AbstractBaseRecord;
import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;
import com.github.hakhakopyan.mydatastream.record.item.Itemable;
import com.github.hakhakopyan.mydatastream.record.item.MyItem;
import com.github.hakhakopyan.mydatastream.record.simple_record.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Представляет собой запись читаему из файла и методы работы с ней и ее модификаци
 */
public class CompositeRecord extends AbstractBaseRecord implements CompositeRecordable, Formatable{

    protected List<Recordable> myRecords = new ArrayList<>();
    String myParentRecordName = "";
    int myNumber = FileReadable.FIRST_RECORD_INDEX - 1;

    /**
     * Устанавливаем имя записи через вызов конструктора {@link AbstractBaseRecord#AbstractBaseRecord(String)}
     * @param name contains record name
     */
    public CompositeRecord(String name) {
        super(name);
    }

    public CompositeRecord(String name, String parentName, int recordNumber) {
        super(name);
        myParentRecordName = parentName;
        this.myNumber = recordNumber;
    }

    public void setRecord(Recordable newRecord) {
        myRecords.add(newRecord);
    }

    @Override
    public void setRecord(Recordable newRecord, String previousRecordName) {
            // решил воспользоваться просто для интереса
            // несколько потоков по логике программы не могут вызвать метод одной записи
            AtomicInteger index = new AtomicInteger(-1);

            for (int i = 0; i < this.myRecords.size(); i++) {
                if (this.myRecords.get(i).getName().toLowerCase().equals(previousRecordName.toLowerCase())) {
                    index.set(i);
                    break;
                }
            }
            if (index.get() > -1) {
                this.myRecords.add(index.get() + 1, newRecord);
            }

    }

    @Override
    public Stream<SimpleRecordable> getSimpleRecordsStream() {
        return this.myRecords.stream()
                    .filter(x -> x.isSimpleRecord())
                    .map(x -> (SimpleRecordable) x);
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
        return new EmptySimpleRecord();
    }

    @Override
    public CompositeRecordable getCompositeRecord(String recordName) {
        List<CompositeRecord> compositeRecords = this.myRecords.stream()
                .filter(x->!x.isSimpleRecord())
                .filter(x->x.getName().toLowerCase().equals(recordName.toLowerCase()))
                .map(x->(CompositeRecord) x)
                .collect(Collectors.toList());

        if (compositeRecords.size() > 0)
            return compositeRecords.get(0);

        return new EmptyCompositeRecord();
    }

    @Override
    public boolean setItemFormat(String itemName, ItemType itemType) {
        try {
            getItem(itemName).setType(itemType);
            return true;
        }catch (ParseException pe) {

        }

        return false;
    }

    @Override
    public String getMyParentRecordName() {
        return myParentRecordName;
    }

    @Override
    public int getIndex() {
        return this.myNumber;
    }

    /**
     * set the ordinal record number
     */
    @Override
    public void setIndex(int index) {
        this.myNumber = index;
    }

    @Override
    public String toString() {
        String retStr = this.getName() + " " + myNumber + "{ ";
        List<String> recRepresent = myRecords.stream().map(r->r.toString()).collect(Collectors.toList());

        if (recRepresent.isEmpty())
            return retStr + "Emty }";

        for (String str: recRepresent) {
            retStr += "\n  " + str;
        }
        return retStr + "\n};\n";
    }

    @Override
    public Iterator<Recordable> iterator() {
        return this.myRecords.iterator();
    }
}
