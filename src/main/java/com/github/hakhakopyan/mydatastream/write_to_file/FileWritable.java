package com.github.hakhakopyan.mydatastream.write_to_file;


import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.IOException;

public interface FileWritable {
    public void write(CompositeRecordable record) throws IOException;
    //public void write(String str) throws IOException;
    public void closeFile() throws IOException;
}
