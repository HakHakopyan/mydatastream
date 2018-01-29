package com.github.hakhakopyan.mydatastream.write_to_file;


import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.IOException;

/**
 * Defines methods for objects that write to files of different record views
 */
public interface FileWritable {

    /**
     * Write to file specified record view
     * @param record contains record specified view of which needs to be written to a file
     * @throws IOException if there are problems with writing in file
     */
    public void write(CompositeRecordable record) throws IOException;

    /**
     * Closes the files or streams to which the record was written
     * @throws IOException if there are problems with the closing file or stream
     */
    public void closeFile() throws IOException;
}
