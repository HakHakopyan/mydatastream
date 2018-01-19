package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

import java.util.HashMap;
import java.util.Map;

public class SQLObjectsContainer {
    public final static int FIRST_OBJECT_ID = 1;
    Map<String, Integer> sqlObjects = new HashMap<String, Integer>();
    private int lastObjectID = 0;

    public boolean isExist(String name) {
        /*
        Predicate<SQLObjectable> predicate = x->x.getObjectName().equals(name);
        if (sqlObjects.stream().anyMatch(predicate))
            return true;
        */
        return sqlObjects.containsKey(name);

    }

    public SQLObjectable add(String name) {
        if (!isExist(name)) {
            //sqlObjects.add(new SQLObject(name, sqlObjects.size() + 1, FIRST_OBJECT_ID));
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
