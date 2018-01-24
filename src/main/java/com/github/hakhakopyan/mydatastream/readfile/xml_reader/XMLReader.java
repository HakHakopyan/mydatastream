package com.github.hakhakopyan.mydatastream.readfile.xml_reader;

import com.github.hakhakopyan.mydatastream.readfile.AbstrFileReader;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class XMLReader extends AbstrFileReader {

    public XMLReader(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    public void readFile(BlockingQueue<CompositeRecordable> writeStream) throws IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(getFile(), new XMLParser(writeStream));
        } catch (ParserConfigurationException | SAXException ex) {
                throw new IOException("File " + getFile().getName() + " XMLParse Exception" + ex.getMessage());
        }
    }
}
