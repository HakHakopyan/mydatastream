package com.github.hakhakopyan.mydatastream.mystream.convertingstream;


import com.github.hakhakopyan.mydatastream.mystream.actionstream.ActionsStream;

/**
 * Реализует инициализацию и выдачу instance of {@link ActionsStream} через методы
 */
public class ConvertingStream {
    /**
     * Реализует инициализацию {@link ActionsStream} списком путей к файлам
     * @param pathes путти к файлам
     * @return instance of {@link ActionsStream}
     */
    public static ActionsStream of(String... pathes) {
        return new ActionsStream(pathes);
    }
}
