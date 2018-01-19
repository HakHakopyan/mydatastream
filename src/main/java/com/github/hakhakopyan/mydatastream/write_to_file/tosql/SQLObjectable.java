package com.github.hakhakopyan.mydatastream.write_to_file.tosql;

public interface SQLObjectable {

    public String getObjectName();

    public int getObjectTypeID();

    public int getLastObjectID();
}
