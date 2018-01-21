package com.github.hakhakopyan.mydatastream.record;

public abstract class AbstractBaseRecord implements Recordable {
    public static final String EMPTY_RECORD_NAME = "EmptyRecord";

    String nodeName;

    public AbstractBaseRecord(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getName() {
        return nodeName;
    }

    public void setName(String recordName) {
        this.nodeName = recordName;
    }
}
