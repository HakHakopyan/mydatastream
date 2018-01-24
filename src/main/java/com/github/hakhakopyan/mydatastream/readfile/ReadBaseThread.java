package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.mystream.convertingstream.ConvertingStream;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class ReadBaseThread implements Runnable {
    String[] myFilePathes;
    BlockingQueue<CompositeRecordable> myWriteStream;

    public ReadBaseThread(BlockingQueue<CompositeRecordable> writeStream, String... filePathes) {
        this.myFilePathes = filePathes;
        this.myWriteStream = writeStream;
    }

    @Override
    public void run() {
        List<FileReadable> fileReaders = Arrays.stream(myFilePathes)
                .map(path->FileReaderFarm.getFileReader(path))
                .collect(Collectors.toList());

        for (FileReadable fileReader: fileReaders) {
            try {
                fileReader.readFile(this.myWriteStream);
            } catch (IOException ex) {
                //вывести в лог
                break;
            }
        }
    }
}
