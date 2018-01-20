package com.github.hakhakopyan.mydatastream.record;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public enum ItemType {
    DATE("dd-mm-yy") {
        public String getRepresent(String str) throws ParseException {
            DateFormat dateFormat = new SimpleDateFormat(this.myFormat, this.myLocale);
            Date date = dateFormat.parse(str);
            return date.toString();
        }
    },
    TEXT("") {
        public String getRepresent(String str) throws ParseException {

            return str;
        }
    };

    String myFormat;
    Locale myLocale = Locale.ENGLISH;

    ItemType(String format) {
        myFormat = format;
    }

    public ItemType setMyFormat(String myFormat) {
        this.myFormat = myFormat;
        return this;
    }

    public ItemType setMyFormat(String myFormat, Locale locale) {
        this.myLocale = myLocale;
        return setMyFormat(myFormat);
    }

    public abstract String getRepresent(String str) throws ParseException;
}
