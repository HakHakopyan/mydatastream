package com.github.hakhakopyan.mydatastream.write_to_file.writer_giver;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;

public interface WriterGivable {
    public FileWritable getWriter(CompositeRecordable record) throws IOException;
    public void closeFile() throws IOException;
}
