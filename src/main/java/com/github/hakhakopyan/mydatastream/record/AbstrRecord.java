package com.github.hakhakopyan.mydatastream.record;

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
