package com.github.hakhakopyan.mydatastream;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import com.github.hakhakopyan.mydatastream.record.composite_record.CompositeRecord;

@Deprecated
public class toJSON {
    static final String params = "src//sqlout//Params.json";
    public static void write(CompositeRecord nodes) {
        /*
        try (JSONWriter jw = new JSONWriter(new BufferedOutputStream(Files.newOutputStream(Paths.get(params))))) {
        } catch (IOException ex) {
            System.out.println("Read-Write exception " + ex.getMessage());
        }
        */

    }
}
