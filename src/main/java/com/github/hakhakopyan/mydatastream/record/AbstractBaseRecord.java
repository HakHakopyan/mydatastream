package com.github.hakhakopyan.mydatastream.record;

/**
 * Реализует сохранение имени записи
 */
public abstract class AbstractBaseRecord implements Recordable {
    public static final String EMPTY_RECORD_NAME = "EmptyRecord";

    String nodeName;

    public AbstractBaseRecord(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * возвращает имя записи
     * @return {@link AbstractBaseRecord#nodeName}
     */
    public String getName() {
        return nodeName;
    }

    /**
     * устанавливаем имя записи
      * @param recordName cjntains Record Name
     */
    public void setName(String recordName) {
        this.nodeName = recordName;
    }
}
