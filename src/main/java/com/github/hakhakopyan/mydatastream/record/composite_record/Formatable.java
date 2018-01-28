package com.github.hakhakopyan.mydatastream.record.composite_record;

import com.github.hakhakopyan.mydatastream.record.item.ItemType;

/**
 * Определяет метод для форматирования значения пол с датой
 */
public interface Formatable {
    /**
     * метод для форматирования значения пол с датой
     * @param itemName contains name of specific instance of {@link ItemType}
     * @param itemType contains {@link ItemType#getRepresent(String)} which format date
     * @return true if format process execute
     */
    boolean setItemFormat(String itemName, ItemType itemType);
}
