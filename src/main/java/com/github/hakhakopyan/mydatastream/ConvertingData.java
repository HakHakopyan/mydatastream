package com.github.hakhakopyan.mydatastream;


import com.github.hakhakopyan.mydatastream.mystream.convertingstream.ConvertingStream;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.UnaryOperator;

public class ConvertingData {

    static String fileName1 = "src//data//input//Library.xml";
    static String fileName2 = "src//data//input//PhoneLibrary.xml";
    static String fileName3 = "src//data//input//Emloyees.xml";

    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());

        //FileReaderGiver fileReader = new FileReaderGiver(fileName1, fileName2);

        // Изменяем значение в поле Cost у Книги, у кторой поле Date равняется 1966
        UnaryOperator<CompositeRecordable> myOp = (r) -> {
            //Длинная запись через получение поля с именем Date, получение у него экземпляра Item и т.д.
            if (r.getSimpleRecord("Date").getItem().getValue().equals("1966")) {
                // Короткая запись - сразу получаем экземпляр Item и устанавливаем значение
                r.getItem("Cost").setValue("15");
            }
            return r;
        };

        ConvertingStream
                .of(fileName1, fileName2)
                .paralelize(2)
                .filter(x -> x.getName() == "Book")
                .change(myOp)
                .format(x->x.setItemFormat("Date", ItemType.DATE.setMyFormat("yyyy")))
                .collect(FileType.SQL);

        ConvertingStream
                .of(fileName1, fileName2, fileName3)
                .collect(FileType.SQL);

        BlockingQueue<CompositeRecordable> bQueue = new LinkedBlockingDeque<>();

        /*
        SAXParserFactory factory = SAXParserFactory.newInstance();

        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(fileName1), new XMLParser(bQueue));
            parser.parse(new File(fileName2), new XMLParser(bQueue));
        } catch (ParserConfigurationException | SAXException ex) {

        }

        FileWritable fileWriter = FileType.SQL.getFileWriter();

        for (CompositeRecordable compositeRecord: bQueue) {
            fileWriter.write(compositeRecord);
        }
        */
    }
}
