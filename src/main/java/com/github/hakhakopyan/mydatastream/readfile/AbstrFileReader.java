package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.File;
import java.io.IOException;

public abstract class AbstrFileReader implements FileReadable {
    File myFile;

    public AbstrFileReader(String fileName) throws IOException {
        myFile = new File(fileName);
    }

    public File getFile() {
        return this.myFile;
    }
}
