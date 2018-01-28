package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализует создание ID
 */
public class SQLObjectsContainer {
    public final static int FIRST_OBJECT_ID = 1;
    Map<String, Integer> sqlObjects = new HashMap<String, Integer>();
    private int lastObjectID = 0;

    /**
     * существование ID под заданным именем
     * @param name
     * @return
     */
    public boolean isExist(String name) {

        return sqlObjects.containsKey(name);
    }

    /**
     * доавляем новое имя и ID для него в наш map и возвращает instance of {@link SQLObjectable}
     * @param name contains record name
     * @return
     */
    public SQLObjectable add(String name) {
        if (!isExist(name)) {
            sqlObjects.put(name, sqlObjects.size() + 1);
        }
        lastObjectID++;

        return getObject(name);
    }

    /**
     * Get SQLObject exemplar with name or create and return if not exit
     * @param name object name
     * @return SQLObject exemplar
     */
    public SQLObjectable getObject(String name) {
        return new SQLObject(name, sqlObjects.get(name), lastObjectID);
    }
}
