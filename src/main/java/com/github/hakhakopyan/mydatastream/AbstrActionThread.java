package main.java.mystream;

import main.java.read_file.FileReaderGivable;
import main.java.write_to_file.FileWritable;

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
