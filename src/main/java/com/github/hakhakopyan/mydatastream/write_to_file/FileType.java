package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.write_to_file.tosql.ToSQL;

import java.io.IOException;

public enum FileType {
    SQL("src//data//out//sql//") {
        @Override
        public FileWritable getFileWriter() throws IOException {
            return new ToSQL(this.myWritePath);
        }

        @Override
        public FileType setPath(String path) {
            this.myWritePath = path;
            return this;
        }
    },
    TXT("src//data//out//") {
        @Override
        public FileWritable getFileWriter() throws IOException{
            return new ToTXT(this.myWritePath);
        }

        @Override
        public FileType setPath(String path) {
            this.myWritePath = path;
            return this;
        }
    },
    XML("src//data//out//xml//") {
        @Override
        public FileWritable getFileWriter() throws IOException{
            return new ToXML(this.myWritePath);
        }

        @Override
        public FileType setPath(String path) {
            this.myWritePath = path;
            return this;
        }
    },
    NOT_WRITE("") {
        @Override
        public FileWritable getFileWriter() throws IOException{
            return new NotWrite();
        }

        @Override
        public FileType setPath(String path) {
            this.myWritePath = path;
            return this;
        }
    };

    String myWritePath;

    FileType(String writePath) {
        this.myWritePath = writePath;
    }

    public abstract FileWritable getFileWriter() throws IOException;
    public abstract FileType setPath(String path);
}
