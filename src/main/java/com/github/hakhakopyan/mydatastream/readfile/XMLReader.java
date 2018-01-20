package com.github.hakhakopyan.mydatastream.readfile;

import com.github.hakhakopyan.mydatastream.record.Record;
import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;
import com.github.hakhakopyan.mydatastream.record.SimpleRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader extends AbstrFileReader {

    public XMLReader(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    Recordable readFile(File file) {
        Recordable readNodes = null;
        //Node root = null;
        Document document = null;
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            //Document document = documentBuilder.parse("Library.xml");
            document = documentBuilder.parse(file);


            // Получаем корневой элемент
            Node root = document.getDocumentElement();
            // записываем имя корневого элемента
            //readNodes = new Record(root.getNodeName());
            // Просматриваем все подэлементы корневого
            readNodes = readNode(root);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return readNodes;
    }

    private  static Recordable readNode(Node node) {
        Recordable readNodes = null;

        NodeList nodeList = node.getChildNodes();
        if (nodeList.getLength() > 1) {
            readNodes = new Record(node.getNodeName());
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node innerNode = nodeList.item(i);
                // Если нода не текст, то это элемент - заходим внутрь
                if (innerNode.getNodeType() != Node.TEXT_NODE) {
                    ((Record) readNodes).setNode(readNode(innerNode));
                }
            }
        } else {
            readNodes = new SimpleRecord(node.getNodeName(), nodeList.item(0).getTextContent());
        }
        return readNodes;
    }

    /*
    private int index = 0;

    @Override
    public synchronized Recordable getRecrod() {
        Node root = this.myDocument.getDocumentElement();
        NodeList myNodeList = root.getChildNodes();
        if (index >= myNodeList.getLength())
            return new EmptyRecord();
        Recordable retRecord = readNode(myNodeList.item(index));
        index++;
        return retRecord;
    }

    @Override
    public boolean isEmpty() {
        if (index >= myDocument.getChildNodes().getLength())
            return true;
        return false;
    }
    */
}
