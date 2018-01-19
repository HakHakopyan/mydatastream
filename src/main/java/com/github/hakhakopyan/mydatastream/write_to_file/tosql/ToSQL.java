package com.github.hakhakopyan.mydatastream.write_to_file.tosql;


import com.github.hakhakopyan.mydatastream.record.Recordable;
import com.github.hakhakopyan.mydatastream.record.SimpleRecordContainer;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;
import representation.SQLRepresentation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ToSQL implements FileWritable {
    static final String PARAMS_FILE = "src//data//out//sql//Params.txt";
    static final String OBJECT_TYPES_FILE = "src//data//out//sql//ObjectTypes.txt";
    static final String OBJECTS_FILE = "src//data//out//sql//Objects.txt";

    static final String PARAMS_TABLE_NAME = "params";
    static final String OBJECT_TYPES_TABLE_NAME = "object_types";
    static final String OBJECTS_TABLE_NAME = "objects";


    SQLObjectsContainer mySQLObjects = new SQLObjectsContainer();

    public ToSQL() {
        createTables();
    }

    public void createTables() {
        createObjectTypesTable();
        createParamTable();
        createObjectsTable();
    }

    private void createParamTable() {
        File fileParams = new File(PARAMS_FILE);
        System.out.println(fileParams + " " + fileParams.exists());
        try (FileWriter fr = new FileWriter(fileParams)) {
            fr.write("CREATE TABLE " + PARAMS_TABLE_NAME + " (" +"\n");
            fr.write("id NUMBER(6) not null,"                   +"\n");
            fr.write("param_name VARCHAR2 not null,"            +"\n");
            fr.write("value_text VARCHAR2,"                     +"\n");
            fr.write("value_date DATE" + " );"                  +"\n\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createObjectTypesTable() {
        File fileObjectTypes = new File(OBJECT_TYPES_FILE);
        try (FileWriter fr = new FileWriter(fileObjectTypes)) {
            fr.write("CREATE TABLE " + OBJECT_TYPES_TABLE_NAME + " ("   +"\n");
            fr.write("object_type_id NUMBER(4) not null,"               +"\n");
            fr.write("param_name VARCHAR2 not null" + ");"              +"\n\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createObjectsTable() {
        File fileObjectTypes = new File(OBJECTS_FILE);
        try (FileWriter fr = new FileWriter(fileObjectTypes)) {
            fr.write("CREATE TABLE " + OBJECTS_TABLE_NAME + " ("    +"\n");
            fr.write("object_type_id NUMBER(4) not null,"           +"\n");
            fr.write("object_id NUMBER(6) not null" + ");"          +"\n\n");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public synchronized void write(Recordable record) throws IOException {
        boolean exist = mySQLObjects.isExist(record.getName());
        SQLObjectable sqlObject = mySQLObjects.add(record.getName());
        if (!exist) {
            writeAtObjectTypesFile(sqlObject);
        }
        writeAtObjectsFile(sqlObject);
        writeAtParamsFile(sqlObject, (SimpleRecordContainer) record);
    }

    @Override
    public void write(String str) throws IOException {

    }

    private boolean writeAtObjectTypesFile(SQLObjectable sqlObject) throws IOException {
        File fileObjectType = new File(OBJECT_TYPES_FILE);
        if (!fileObjectType.exists())
            return false;

        try (FileWriter fw = new FileWriter(fileObjectType, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("INSERT INTO " + OBJECT_TYPES_TABLE_NAME + " VALUES ("
                + sqlObject.getObjectTypeID() + ", '" + sqlObject.getObjectName() + "');\n\n");
        }
        return true;
    }

    private boolean writeAtObjectsFile(SQLObjectable sqlObject) throws IOException {
        File file = new File(OBJECTS_FILE);
        if (!file.exists())
            return false;

        try (FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("INSERT INTO " + OBJECTS_TABLE_NAME + " VALUES ("
                    + sqlObject.getObjectTypeID() + ", " + sqlObject.getLastObjectID() + ");\n\n");
        }
        return true;
    }

     private boolean writeAtParamsFile(SQLObjectable sqlObject, SimpleRecordContainer record) throws IOException {
         File file = new File(PARAMS_FILE);
         if (!file.exists())
             return false;

        //String dataWithNewLine = data + System.getProperty("line.separator");
        try (FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("INSERT ALL\n");

            List<String> sqlRecordRepresent = SQLRepresentation
                    .GetRepresentation(record)
                    .map(x -> "INTO " + PARAMS_TABLE_NAME + " VALUES(" + sqlObject.getLastObjectID() + "," + x + ")\n")
                    .collect(Collectors.toList());

            for (String st: sqlRecordRepresent) {
                    bw.write(st);
            }

            bw.write("SELECT * FROM dual;\n\n");
        }
        return true;
    }

    @Override
    public void closeFile() throws IOException {

    }
}