package com.github.hakhakopyan.mydatastream.record;

import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class EmptyRecord implements Recordable {
    public static final String EMPTY_RECORD_NAME = "EmptyRecord";

    @Override
    public boolean IsSimpleNode() {
        return false;
    }

    @Override
    public boolean IsEmptyRecord() {
        return true;
    }

    @Override
    public void print(PrintWriter pr) {

    }

    @Override
    public void print(BufferedWriter out) {

    }

    @Override
    public String getName() {
        return EMPTY_RECORD_NAME;
    }
}
