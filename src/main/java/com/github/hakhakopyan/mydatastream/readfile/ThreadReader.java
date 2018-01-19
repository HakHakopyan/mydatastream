package com.github.hakhakopyan.mydatastream.readfile;

import java.io.IOException;

@Deprecated
public class ThreadReader implements Runnable {
    String filePath;
    FileReaderGiver myFileReader;

    public ThreadReader(String filePath, FileReaderGiver fileReader) {
        this.filePath = filePath;
        this.myFileReader = fileReader;
    }

    @Override
    public void run() {
        String fileType = String.valueOf(filePath.substring(filePath.lastIndexOf(".") + 1)).toUpperCase();
        try {
            FileReadable fileReader = null;
            switch (fileType) {
                case "XML":
                    fileReader = new XMLReader(filePath);
                    break;
            }

            System.out.println(this.toString());

            //myFileReader.addFileReader(fileReader);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
