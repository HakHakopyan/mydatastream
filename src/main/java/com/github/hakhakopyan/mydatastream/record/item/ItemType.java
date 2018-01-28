package com.github.hakhakopyan.mydatastream.record.item;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Тип, которые может принимать значение поля
 */
public enum ItemType {
    /**
     * При установлении этого типа мы задаем форматирование, которое применится к знаению
     */
    DATE("dd-mm-yy") {
        public String getRepresent(String str) throws ParseException {
            DateFormat dateFormat = new SimpleDateFormat(this.myFormat, this.myLocale);
            Date date = dateFormat.parse(str);
            return date.toString();
        }
    },
    /**
     * не применяем никакого форматирования
     */
    TEXT("") {
        public String getRepresent(String str) throws ParseException {
            return str;
        }
    },
    /**
     * Не применяем форматрования
     */
    NULL("") {
        public String getRepresent(String str) throws ParseException {
            return "NULL";
        }
    };

    String myFormat;
    Locale myLocale = Locale.ENGLISH;

    /**
     * Задаем форматирование
     * @param format содержит представление даты в соответсвующем поле записи, допустим 1900 - yyyy
     */
    ItemType(String format) {
        myFormat = format;
    }

    /**
     * установить представление даты в соответсвующем поле записи, допустим 1900 - yyyy
     * @param myFormat содержит представление даты в соответсвующем поле записи, допустим 1900 - yyyy
     * @return экземпляр {@link ItemType} у котрого метод был вызван
     */
    public ItemType setMyFormat(String myFormat) {
        this.myFormat = myFormat;
        return this;
    }

    /**
     * установить представление даты в соответсвующем поле записи, допустим 1900 - yyyy
     * @param myFormat содержит представление даты в соответсвующем поле записи, допустим 1900 - yyyy
     * @param locale содержит регион
     * @return экземпляр {@link ItemType} у котрого метод был вызван
     */
    public ItemType setMyFormat(String myFormat, Locale locale) {
        this.myLocale = myLocale;
        return setMyFormat(myFormat);
    }

    /**
     * Получить форматированное представление даты
     * @param str содержит дату для которой нужно получить форматированное представление
     * @return форматированное представление переданной в метод даты
     * @throws ParseException если форматировать невозможно
     */
    public abstract String getRepresent(String str) throws ParseException;
}
