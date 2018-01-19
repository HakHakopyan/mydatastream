package com.github.hakhakopyan.mydatastream.readfile;


import com.github.hakhakopyan.mydatastream.record.Recordable;

public interface FileReadable {
    public Recordable getRecord();
    public boolean isEmpty();
}
