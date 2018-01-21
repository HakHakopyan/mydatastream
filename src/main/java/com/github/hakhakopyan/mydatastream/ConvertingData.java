package com.github.hakhakopyan.mydatastream;


import com.github.hakhakopyan.mydatastream.mystream.convertingstream.ConvertingStream;
import com.github.hakhakopyan.mydatastream.readfile.FileReaderGiver;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileType;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ConvertingData {

    static String fileName1 = "src//data//input//Library.xml";
    static String fileName2 = "src//data//input//PhoneLibrary.xml";
    public static void main(String[] args) throws IOException {
        System.out.println(Runtime.getRuntime().availableProcessors());

        FileReaderGiver fileReader = new FileReaderGiver(fileName1, fileName2);
        Predicate<CompositeRecord> myPr = x->x.isEmpty();
        myPr.and(x->x.getName()=="book");
        /*
        Nodable nodes = ReadXML.ReadFile(new File(fileName));
        ConvertingStream.of(fileName);
        */
        UnaryOperator<CompositeRecordable> myOp = (r) -> {
            if (r.getSimpleRecord("Date").getItem().getValue().equals("1966"))
                r.getSimpleRecord("Cost").getItem().setValue("15");
            return r;
        };
        ConvertingStream
                .of(fileName1, fileName2)
                .paralelize(2)
                .filter(x -> x.getName() == "Book")
                .change(myOp)
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
