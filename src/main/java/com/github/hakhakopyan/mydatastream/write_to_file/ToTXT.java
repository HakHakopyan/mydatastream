package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Implements to write the Record view in a text file
 */
public class ToTXT implements FileWritable {
    String TXT_FILE_NAME = "Writer.txt";
    FileWriter myFR;
    BufferedWriter myBR;

    public ToTXT(String path) throws IOException {
        TXT_FILE_NAME = path + TXT_FILE_NAME;
        File file = new File(TXT_FILE_NAME);
        if (file.exists())
            System.out.println(file.getName());
        myFR = new FileWriter(file);
        myBR = new BufferedWriter(myFR);
    }

    @Override
    public synchronized void write(CompositeRecordable record) throws IOException {
        if (record.isEmpty())
            return;
        synchronized(this.myBR) {
            this.myBR.write(record.toString());
        }
    }

    public void closeFile() throws IOException {
        myBR.close();
        myFR.close();
    }
}