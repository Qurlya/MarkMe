package ArcheAge.MarkMe.Ctrl.Helpyshka;

import ArcheAge.MarkMe.Variables.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLink {
    private static String json;
    private static ObjectMapper mapper = new ObjectMapper();
    private static ArcheAge.MarkMe.Variables.Record record;

    public static String write(ArcheAge.MarkMe.Variables.Record record) {
        try {
            json = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static ArcheAge.MarkMe.Variables.Record read(String json) {
        try {
            record = mapper.readValue(json, Record.class);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return record;
    }
}

