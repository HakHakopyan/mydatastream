package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.IOException;

public class NotWrite implements FileWritable {

    @Override
    public void write(CompositeRecordable record) throws IOException {

    }

    @Override
    public void closeFile() throws IOException {

    }
}
