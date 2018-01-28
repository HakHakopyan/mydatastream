package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;

import java.io.File;
import java.io.IOException;

/**
 * Хранит и инициализирует поле {@link AbstrFileReader#myFile}
 */
public abstract class AbstrFileReader implements FileReadable {
    File myFile;

    /**
     * Инициализация поля {@link AbstrFileReader#myFile}
     * @param fileName содержит путь и имя к файлу
     * @throws IOException путь не действительный
     */
    public AbstrFileReader(String fileName) throws IOException {
        myFile = new File(fileName);
    }

    /**
     * Возвращаем поле {@link AbstrFileReader#myFile}
     * @return instance of {@link File}
     */
    public File getFile() {
        return this.myFile;
    }
}
