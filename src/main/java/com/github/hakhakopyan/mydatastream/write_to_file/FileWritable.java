package com.github.hakhakopyan.mydatastream.write_to_file;


import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;

import java.io.IOException;

public interface FileWritable {
    public void write(Recordable record) throws IOException;
    public void write(String str) throws IOException;
    public void closeFile() throws IOException;
}
