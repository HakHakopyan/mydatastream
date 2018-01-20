package com.github.hakhakopyan.mydatastream.record.recordinterfaces;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public interface Recordable {
    public boolean IsSimpleNode();
    public boolean IsEmptyRecord();
    public String getName();

    //public Recordable Filter(Filterable screen);
    public void print(PrintWriter pr);
    public void print(BufferedWriter out);
}
