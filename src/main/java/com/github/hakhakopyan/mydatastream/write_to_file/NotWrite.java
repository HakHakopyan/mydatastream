package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.IOException;

/**
 * It is used if nothing needs to be written down,
 * but in the logic of the program it is possible to access the object implements {@link FileWritable},
 * to write record representation to a file
 */
public class NotWrite implements FileWritable {

    /**
     * Nothing is done
     * @param record contains record specified view of which needs to be written to a file
     * @throws IOException Never mind
     */
    @Override
    public void write(CompositeRecordable record) throws IOException {

    }

    /**
     * Nothing is done
     * @throws IOException never mind
     */
    @Override
    public void closeFile() throws IOException {

    }
}
