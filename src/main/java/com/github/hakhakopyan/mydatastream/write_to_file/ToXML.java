package com.github.hakhakopyan.mydatastream.write_to_file;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.representation.XMLRepresentation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ToXML implements FileWritable{
    String XML_FILE_NAME = ".xml";
    String myPath;
    FileWriter myFR;
    BufferedWriter myBR;
    String myRecorodsParentName = "";

    public ToXML(String path) throws IOException {
        myPath = path;
    }

    @Override
    public synchronized void write(CompositeRecordable record) throws IOException {
        if (myRecorodsParentName == "")
            createFile(record.getMyParentRecordName());

        if (myRecorodsParentName != record.getMyParentRecordName())
            return;

        synchronized(this.myBR) {
            List<String> recordRepresent = XMLRepresentation.GetRepresentation(record, 1);
            for (String str:recordRepresent) {
                myBR.write(str);
            }
        }
    }

    private void createFile(String parentName) throws IOException {
        myRecorodsParentName = parentName;
        XML_FILE_NAME = myPath + myRecorodsParentName + XML_FILE_NAME;
        File file = new File(XML_FILE_NAME);
        if (file.exists())
            System.out.println(file.getName());
        myFR = new FileWriter(file);
        myBR = new BufferedWriter(myFR);
        myBR.write("<" + myRecorodsParentName + ">" + "\n");
    }

    public void closeFile() throws IOException {
        myBR.write("</" + myRecorodsParentName + ">");
        myBR.close();
        myFR.close();
    }
}
