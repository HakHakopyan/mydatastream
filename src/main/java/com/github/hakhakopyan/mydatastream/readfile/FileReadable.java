package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * Interface for objects that read files with records
 */
public interface FileReadable {
    // The index assigned to the first read Record from the file
    int FIRST_RECORD_INDEX = 0;

    /**
     * read file and write records in writeStream
     * @param writeStream Contains the object in which we write the read records
     * @throws IOException if if there is a problem with reading the record method throws exception with a description of the error
     */
    void readFile(BlockingQueue<CompositeRecordable> writeStream) throws IOException;
}
