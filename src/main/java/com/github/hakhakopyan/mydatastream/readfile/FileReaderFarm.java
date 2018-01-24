package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.readfile.xml_reader.XMLReader;

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
