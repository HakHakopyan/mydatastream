package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

/**
 * Необходим во время записи в файл пердставления Record
 * Содержит Имя записи, уникальное ID соотвествующее этому имени
 * и уникальное ID для каждой записи из группы записей с схожими именами
 */
public class SQLObject implements SQLObjectable {
    private String objectName;
    private int objectTypeID;
    private int lastObjectID;

    public SQLObject(String objectName, int objectTypeID, int lastObjectID) {
        this.objectName = objectName;
        this.objectTypeID = objectTypeID;
        this.lastObjectID = lastObjectID;
    }

    public String getObjectName() {
        return objectName;
    }

    public int getObjectTypeID() {
        return objectTypeID;
    }

    public int getLastObjectID() {
        return lastObjectID;
    }
}
