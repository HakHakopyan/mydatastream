package com.github.hakhakopyan.mydatastream.mystream;


import com.github.hakhakopyan.mydatastream.readfile.FileReaderGivable;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGiver;

public class ConvertingStream {
    public static ActionsStream of(String... pathes) {
        FileReaderGivable fileReader = new FileReaderGiver(pathes);

        return new ActionsStream(fileReader);
    }
}
