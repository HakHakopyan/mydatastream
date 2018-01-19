package com.github.hakhakopyan.mydatastream.readfile;

import java.io.IOException;

public class FileReaderFarm {
    public static FileReadable getFileReader(String filePath) {
        String fileType = String.valueOf(filePath.substring(filePath.lastIndexOf(".") + 1)).toUpperCase();
        FileReadable fileReader = null;
        try {
            switch (fileType) {
                case "XML":
                    fileReader = new XMLReader(filePath);
                    break;
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return fileReader;
    }
}
