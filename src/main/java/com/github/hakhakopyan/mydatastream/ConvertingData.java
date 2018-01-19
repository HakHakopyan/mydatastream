package com.github.hakhakopyan.mydatastream;


import com.github.hakhakopyan.mydatastream.mystream.ConvertingStream;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGiver;
import com.github.hakhakopyan.mydatastream.record.Record;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;

import java.io.IOException;
import java.util.function.Predicate;

public class ConvertingData {

    static String fileName1 = "src//data//input//Library.xml";
    static String fileName2 = "src//data//input//PhoneLibrary.xml";
    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());

        FileReaderGiver fileReader = new FileReaderGiver(fileName1, fileName2);
        Predicate<Record> myPr = x->x.IsEmptyRecord();
        myPr.and(x->x.getName()=="book");
        /*
        Nodable nodes = ReadXML.ReadFile(new File(fileName));
        ConvertingStream.of(fileName);
        */
        ConvertingStream
                .of(fileName1, fileName2)
                .paralelize(2)
                .filter(x->x.getName() == "person" || x.getName() == "Book")
                .collect(FileType.SQL);

        /*
        try (PrintWriter pw = new PrintWriter(System.out)) {
            //fileReader.GetNode().print(pw);
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }

        Stream<String> stringStream = Files.lines(Paths.get(fileName1));
        */
        //main.java.write_to_file.to_sql.ToSQL.createTables();
        //main.java.write_to_file.to_sql.ToSQL.writeParamsAtFile(nodes);


    }
}
