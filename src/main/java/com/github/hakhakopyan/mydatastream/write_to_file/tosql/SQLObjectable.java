package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;

/**
 * Реализует методы для работы с объектом помогающим при записи объекта в sql tables
 * В проекте объектом выступает конкретная запись {@link CompositeRecord}
 */
public interface SQLObjectable {
    /**
     * возвращает имя составной записи
     * @return имя составной записи
     */
    public String getObjectName();

    /**
     * возвращает ID объекта, у каждого вида записи (Имени) свое значение ID
     * ID name of record
     * @return ID name of record
     */
    public int getObjectTypeID();

    /**
     * содержит порядковый номер записи в череде записей с одинаковым именем
     * @return Id record in a series of records with the same name
     */
    public int getLastObjectID();
}
