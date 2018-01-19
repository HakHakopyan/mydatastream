package com.github.hakhakopyan.mydatastream.mystream;

import main.java.read_file.FileReaderGivable;
import main.java.read_file.FileReaderGiver;

public class ConvertingStream {
    public static ActionsStream of(String... pathes) {
        FileReaderGivable fileReader = new FileReaderGiver(pathes);

        return new ActionsStream(fileReader);
    }
}
