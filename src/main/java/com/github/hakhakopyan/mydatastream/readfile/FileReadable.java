package com.github.hakhakopyan.mydatastream.readfile;

import main.java.record.Recordable;

public interface FileReadable {
    public Recordable getRecord();
    public boolean isEmpty();
}
