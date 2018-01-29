package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализует создание и поддержание уникальности ID, соответсвующее имени объекта,
 * и ID соотвествующее группе объектов с схожим именем
 */
public class SQLObjectsContainer {
    public final static int FIRST_OBJECT_ID = 1;
    Map<String, Integer> sqlObjects = new HashMap<String, Integer>();
    private int lastObjectID = 0;

    /**
     * существование ID под заданным именем
     * @param name имя объекта, для которого нужно проверить наличие ID
     * @return true, если такое имя объекта ранее был добавлен через вызов {@link SQLObjectsContainer#add(String)}
     */
    public boolean isExist(String name) {

        return sqlObjects.containsKey(name);
    }

    /**
     * добавляет новое имя и уникальное ID для него в наш map
     * и возвращает instance of {@link SQLObjectable} с уникальным ID для Гурппы объектов с схожими именами
     * @param name contains object name
     * @return instance of {@link SQLObjectable} с уникальными ID
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
