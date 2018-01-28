package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.readfile.xml_reader.XMLReader;

import java.io.IOException;

/**
 * Содержит метод возвращающий экземпляр реализующего интерфейс {@link FileReadable}
 * выбор основывается на расширении имени файла
 */
public class FileReaderFarm {
    /**
     * Возвращает экземпляр реализующего интерфейс {@link FileReadable}
     * выбор основывается на расширении имени файла
     * @param filePath содержит имя файла
     * @return экземпляр реализующего интерфейс {@link FileReadable}
     */
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
