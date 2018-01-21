package com.github.hakhakopyan.mydatastream.mystream.actionthread;

import com.github.hakhakopyan.mydatastream.Actions.Actionable;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGivable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;
import java.util.List;

public class ActionThread extends AbstrActionThread {

    public ActionThread(List<Actionable> actions, FileReaderGivable reader, FileWritable writer) {
        super(actions, reader, writer);
    }

    @Override
    public void run() {
        CompositeRecordable record;
        while (!(record  = myReader.getFileReader().getCompositeRecord()).isEmpty()) {
            for (Actionable action: myActions) {
                record = action.action(record);
                if (record.isEmpty())
                    break;
            }

            if (!record.isEmpty()) {
                synchronized (myWriter) {
                    try {
                        myWriter.write(Thread.currentThread().getName());
                        myWriter.write(record);
                    } catch (IOException ex) {

                    }
                }
            }
        }
    }

}
