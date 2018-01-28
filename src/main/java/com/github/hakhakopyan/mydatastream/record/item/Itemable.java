package com.github.hakhakopyan.mydatastream.record.item;

import java.text.ParseException;

/**
 * Определяет методы для реализации значение поля записи
 */
public interface Itemable {
    /**
     * Возвращает значение поля записи
     * @return значение поля записи
     */
    String getValue();

    /**
     * Устанавливает значение поля записи
     * @param value содержит устанавливаемое значение
     */
    void setValue(String value);

    /**
     * возвращает тип значения поля записи {@link ItemType}
     * @return тип значения поля записи
     */
    ItemType getType();

    /**
     * Устанавливает значение поля записи {@link ItemType}
     * @param type тип значения поля записи, которое выполняет еще и форматирование этого значения
     * @throws ParseException если проблемы при форматировании значения
     */
    void setType(ItemType type) throws ParseException;
}
