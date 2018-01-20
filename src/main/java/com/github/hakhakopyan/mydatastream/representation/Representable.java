package com.github.hakhakopyan.mydatastream.representation;


import com.github.hakhakopyan.mydatastream.record.recordinterfaces.Recordable;

import java.util.stream.Stream;

public interface Representable {
    public Stream<String> GetRepresentation(Recordable baseRecord);
}
