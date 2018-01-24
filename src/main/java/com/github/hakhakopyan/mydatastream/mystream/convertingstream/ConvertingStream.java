package com.github.hakhakopyan.mydatastream.mystream.convertingstream;


import com.github.hakhakopyan.mydatastream.mystream.actionstream.ActionsStream;

public class ConvertingStream {
    public static ActionsStream of(String... pathes) {
        return new ActionsStream(pathes);
    }
}
