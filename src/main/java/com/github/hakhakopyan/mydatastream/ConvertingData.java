package com.github.hakhakopyan.mydatastream;


import com.github.hakhakopyan.mydatastream.mystream.convertingstream.ConvertingStream;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGiver;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.item.ItemType;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;

import java.io.IOException;
import java.util.function.UnaryOperator;

public class ConvertingData {

    static String fileName1 = "src//data//input//Library.xml";
    static String fileName2 = "src//data//input//PhoneLibrary.xml";
    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());

        FileReaderGiver fileReader = new FileReaderGiver(fileName1, fileName2);

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
    }
}
