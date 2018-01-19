package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.write_to_file.tosql.ToSQL;

import java.io.IOException;

public enum FileType {
    SQL {
        @Override
        public FileWritable getFileWriter() throws IOException {
            return new ToSQL();
        }
    },
    TXT {
        @Override
        public FileWritable getFileWriter() throws IOException{
            return new ToTXT();
        }
    };

    public abstract FileWritable getFileWriter() throws IOException;
}
