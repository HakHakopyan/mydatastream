package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecordable;
import com.github.hakhakopyan.mydatastream.write_to_file.FileWritable;
import com.github.hakhakopyan.mydatastream.representation.SQLRepresentation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Релизует запись в заданные SQL таблицы SQL представления записи
 */
public class ToSQL implements FileWritable {
    String PARAMS_FILE = "Params.txt";
    String OBJECT_TYPES_FILE = "ObjectTypes.txt";
    String OBJECTS_FILE = "Objects.txt";

    static final String PARAMS_TABLE_NAME = "params";
    static final String OBJECT_TYPES_TABLE_NAME = "object_types";
    static final String OBJECTS_TABLE_NAME = "objects";


    SQLObjectsContainer mySQLObjects = new SQLObjectsContainer();

    /**
     * Инициализация пути к файлам для записи sql пердставления записи
     * @param path путь к файлам
     */
    public ToSQL(String path) {
        PARAMS_FILE = path + PARAMS_FILE;
        OBJECT_TYPES_FILE = path + OBJECT_TYPES_FILE;
        OBJECTS_FILE = path + OBJECTS_FILE;
        createTables();
    }

    /**
     * writing to the corresponding files requests to create SQL tables
     */
    public void createTables() {
        createObjectTypesTable();
        createParamTable();
        createObjectsTable();
    }

    /**
     * Write a query to create a table that contains the record fields
     */
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

    /**
     * Write a request to create a table containing the name of the record and the corresponding ID
     */
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

    /**
     * Write a request to create a table that contains the ID of the record name
     * and a unique ID for each record from the group of records with a similar name
     */
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

    /**
     * Writing to the appropriate file requests to add SQL representation of record
     * @param record Record, the representation of which should be written in the SQL tables
     * @throws IOException if we have problems with writing to a file
     */
    @Override
    public synchronized void write(CompositeRecordable record) throws IOException {
        if (record.isEmpty())
            return;
        boolean exist = mySQLObjects.isExist(record.getName());
        SQLObjectable sqlObject = mySQLObjects.add(record.getName());
        if (!exist) {
            writeAtObjectTypesFile(sqlObject);
        }
        writeAtObjectsFile(sqlObject);
        writeAtParamsFile(sqlObject, record);
    }

    /**
     * запись в файл запроса на добавление в таблицу с именем {@link ToSQL#OBJECT_TYPES_TABLE_NAME}
     * уникального ID сответствующее имени Записи
     * @param sqlObject содержит имя записи и уникальное ID для этого имени
     * @return true if writing to the file was successful
     * @throws IOException if we have problems with writing to a file
     */
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

    /**
     * creating a request to add an ID corresponding to the record name
     * and the record ID from the group of records with the same name
     * @param sqlObject contains unique ID
     * @return true if writing to the file was successful
     * @throws IOException if we have problems with writing to a file
     */
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

    /**
     * Create a request to add record fields in the table named {@link ToSQL#PARAMS_TABLE_NAME}
     * @param sqlObject contains unique ID
     * @param record Contains a Record whose fields need to be written to a SQL table named {@link ToSQL#PARAMS_TABLE_NAME}
     * @return true if writing to the file was successful
     * @throws IOException if we have problems with writing to a file
     */
     private boolean writeAtParamsFile(SQLObjectable sqlObject, CompositeRecordable record) throws IOException {
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
