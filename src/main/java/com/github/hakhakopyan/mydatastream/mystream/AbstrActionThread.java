package com.github.hakhakopyan.mydatastream.mystream;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGivable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.util.List;

public abstract class AbstrActionThread implements Runnable {
    List<Actionable> myActions;
    FileReaderGivable myReader;
    FileWritable myWriter;

    public AbstrActionThread(List<Actionable> actions, FileReaderGivable reader, FileWritable writer) {
        this.myActions = actions;
        myReader = reader;
        myWriter = writer;
    }
}
