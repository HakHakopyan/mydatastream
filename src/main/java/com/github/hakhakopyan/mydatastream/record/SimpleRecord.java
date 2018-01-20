package com.github.hakhakopyan.mydatastream.record;

import com.github.hakhakopyan.mydatastream.record.item.Item;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleRecord extends AbstrRecord {

    Item myItem;

    public SimpleRecord(String name, Item item) {
        super(name);
        this.myItem = new Item(item);
    }

    public SimpleRecord(String name, String value) {
        super(name);
        this.myItem = new Item(value, ItemType.TEXT);
    }

    public Item getItem() {
        return this.myItem;
    }

    public void print(PrintWriter pr) {
        pr.println(nodeName + " " + myItem.getValue() + " " + myItem.getType());
    }

    public void print(BufferedWriter out) {
        try {
            out.write(nodeName + " " + myItem.getValue() + " " + myItem.getType() + "\n");
        } catch (IOException ex) {
            System.out.println("Read-Write exception " + ex.getMessage());
        }
    }

    /*
    public Recordable Filter(Conditionable screen) {
        Record retNodes = new Record(this.getName());
        if (screen.Execute(this))
            return this;
        return null;
    }
    */

    @Override
    public boolean IsSimpleNode() {
        return true;
    }

    @Override
    public boolean IsEmptyRecord() {
        return false;
    }
}
