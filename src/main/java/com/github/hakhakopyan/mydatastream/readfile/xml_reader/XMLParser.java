package com.github.hakhakopyan.mydatastream.readfile.xml_reader;

import com.github.hakhakopyan.mydatastream.readfile.FileReadable;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;
import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.record.simple_record.SimpleRecord;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Читает XML file без загрузки его в опертаивку и записывает по одной записи в {@link XMLParser#writeStream}
 */
public class XMLParser extends DefaultHandler2 {
    /**
     * В это поле идет запись считываемых записей
     */
    BlockingQueue<CompositeRecordable> writeStream;
    /**
     * Имя поля в XML файле которое орамляет все читаемые записи
     */
    String myBaseNodeName;
    /**
     * Содержит имя записи которая не вляется составной, и содержит только значение
     */
    String mySimpleRecordName = "";
    /**
     * Буферный стэк для записи промежуточных записей до формирования окончательной составной записи
     */
    Stack<CompositeRecordable> myChainOfCompositeRecords = new Stack<CompositeRecordable>();
    //по логике программы несолько потоков не могут воспользоваться одним парсером
    // поэтому пользуюсь ради интереса
    AtomicInteger myRecordNumber = new AtomicInteger(FileReadable.FIRST_RECORD_INDEX);

    /**
     * Инициализируем поле {@link XMLParser#writeStream}
     * @param writeStream объект, в который будет осуществляться запись читаемых из файла Записей
     */
    public XMLParser(BlockingQueue<CompositeRecordable> writeStream) {
        this.writeStream = writeStream;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        myBaseNodeName = "";
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (myBaseNodeName.equals("")) {
            myBaseNodeName = qName;
            return;
        }

        if (!mySimpleRecordName.equals("")) {
            if (myChainOfCompositeRecords.size() == 0) {
                myChainOfCompositeRecords.push(
                        new CompositeRecord(mySimpleRecordName, myBaseNodeName, myRecordNumber.getAndIncrement()));
            } else {
                myChainOfCompositeRecords.push(new CompositeRecord(mySimpleRecordName));
            }
        }
        mySimpleRecordName = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        mySimpleRecordName = "";

        if (myChainOfCompositeRecords.size() == 0) {
            return;
        }

        if (myChainOfCompositeRecords.size() == 1) {
            if (myChainOfCompositeRecords.peek().getName().equals(qName))
                //Если закрылась основная нода, то отправляем его на выход
                this.writeStream.add(myChainOfCompositeRecords.pop());
        } else {
            // если закрылоась ода из вложенных нод, то добавляем ее в предыдущую
            CompositeRecordable child = myChainOfCompositeRecords.pop();
            myChainOfCompositeRecords.peek().setRecord(child);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (mySimpleRecordName.equals(""))
            return;;
        String characters = (new String(ch, start, length)).trim();
        if (characters.equals(""))
            return;
        if (myChainOfCompositeRecords.size() >= 1) {
            CompositeRecordable compositeRecord = myChainOfCompositeRecords.pop();
            compositeRecord.setRecord(new SimpleRecord(mySimpleRecordName, new String(ch, start, length)));
            myChainOfCompositeRecords.push(compositeRecord);
        }
    }
}
