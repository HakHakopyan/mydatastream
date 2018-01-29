package com.github.hakhakopyan.mydatastream.write_to_file.writer_giver;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;

import java.io.IOException;

/**
 * Defines a method for giving objects that implement record write in file
 */
public interface WriterGivable {
    /**
     * Returns a specialized object for writing in the file file a record view, specified in the parameter
     * @param record Specifies the Record for which wanted to get the writer to the file
     * @return An object that writes a record to a file
     * @throws IOException if we problems with write in file or file path
     */
    public FileWritable getWriter(CompositeRecordable record) throws IOException;

    /**
     * close all streams for working with files from objects that can write to a file
     * @throws IOException if we have problems with close stream for working with files
     */
    public void closeFile() throws IOException;
}
