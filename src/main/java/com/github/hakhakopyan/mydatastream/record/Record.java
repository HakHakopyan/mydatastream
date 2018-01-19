package com.github.hakhakopyan.mydatastream.record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Record extends AbstrRecord implements SimpleRecordContainer {

    List<Recordable> myNodes = new ArrayList<>();

    public Record(String name) {
        super(name);
    }

    public void setNode(Recordable newNode) {
        myNodes.add(newNode);
    }

    public synchronized Recordable getNode() {
        if (myNodes.size() <= 0)
            return new EmptyRecord();

        //Record retNode = new Record(this.getName());
        //retNode.setNode(myNodes.remove(0));

        return myNodes.remove(0);
    }

    public void print(PrintWriter pr) {
        pr.println(nodeName);
        myNodes.stream().forEach(x->x.print(pr));
    }

    public void print(BufferedWriter out) {
        try {
            out.write(this.nodeName + "\n");
            this.myNodes.stream().forEach(x->x.print(out));
    } catch (IOException ex) {
            System.out.println("Read-Write exception " + ex.getMessage());
        }
    }

    @Override
    public SimpleRecordContainer GetRecordContainSimpleNodes() {
        Record node = this;
        while (!node.myNodes.get(0).IsSimpleNode()) {
            node = (Record) node.myNodes.get(0);
        }
        return node;
    }

    @Override
    public Stream<SimpleRecord> getSimpleRecordsStream() {
        return this.myNodes.stream()
                .filter(x->x.IsSimpleNode()).map(x->(SimpleRecord)x);
    }

    /*
    public Item getItem(String recordName) {
        Item retItem = Item.getEmptyItem();

        List<Recordable> records = myNodes.parallelStream()
                .filter(x->x.getName().toLowerCase().equals(recordName.toLowerCase()))
                .collect(Collectors.toList());

        for (Recordable record:records) {
            if (record.IsEmptyRecord())
                retItem = ((SimpleRecord)record).getItem();
        }

        return retItem;
    }
    */

    @Override
    public boolean IsSimpleNode() {
        return false;
    }

    @Override
    public boolean IsEmptyRecord() {
        return false;
    }
}
