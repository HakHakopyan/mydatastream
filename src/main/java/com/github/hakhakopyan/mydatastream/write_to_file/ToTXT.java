package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ToTXT implements FileWritable {
    static final String TXT_FILE_NAME = "com.github.hakhakopyan.convertingdata.src\\Writer.txt";
    FileWriter myFR;
    BufferedWriter myBR;

    public ToTXT() throws IOException {
        File file = new File(TXT_FILE_NAME);
        myFR = new FileWriter(file);
        myBR = new BufferedWriter(myFR);
    }

    @Override
    public synchronized void write(Recordable record) {
        synchronized(this.myBR) {
                record.print(this.myBR);
            }
    }

    public void write(String str) {
            try {
                this.myBR.write(str + "\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public void closeFile() throws IOException {
        myBR.close();
        myFR.close();
    }
}