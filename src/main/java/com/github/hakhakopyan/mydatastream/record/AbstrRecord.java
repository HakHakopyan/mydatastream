package com.github.hakhakopyan.mydatastream.record;

import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;

public abstract class AbstrRecord implements Recordable {
    String nodeName;

    public AbstrRecord(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getName() {
        return nodeName;
    }

    public void setName(String nodeName) {
        this.nodeName = nodeName;
    }
}
