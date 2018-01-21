package com.github.hakhakopyan.mydatastream.mystream.actionstream;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.Actions.Filter;
import com.github.hakhakopyan.mydatastream.mystream.actionthread.ActionBaseThread;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGivable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ActionsStream {
    List<Actionable> myActions = new ArrayList<>();

    int threadCount = 2;
    //List<Nodable> myNodesList = new ArrayList<>();

    FileReaderGivable myFileReader;

    public ActionsStream(FileReaderGivable fileReader) {
        this.myFileReader = fileReader;
    }

    public ActionsStream paralelize() {
        return paralelize(2);
    }

    public ActionsStream paralelize(int threadCount) {
        this.threadCount = threadCount;

        return this;
    }

    public ActionsStream filter(Predicate<CompositeRecordable> condition) {
        myActions.add(new Filter(condition));

        return this;
    }

    //public ActionsStream format(UnaryOperator<>)

    public void collect(FileType fileType) throws IOException {
        FileWritable fileWriter = fileType.getFileWriter();
       Thread baseAction = new Thread(
                    new ActionBaseThread(myActions, myFileReader, fileWriter,
                            threadCount), "ActionBaseStream");
       baseAction.start();
       try {
           baseAction.join();
       } catch (InterruptedException ex) {
           System.out.println(ex);
       }

       fileWriter.closeFile();
    }
}
