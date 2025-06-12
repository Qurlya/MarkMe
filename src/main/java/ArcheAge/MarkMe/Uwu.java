package ArcheAge.MarkMe;

import ArcheAge.MarkMe.Variables.Request;
import ArcheAge.MarkMe.Variables.JsonLink;
import ArcheAge.MarkMe.Variables.Record;

public class Uwu {
    public static void main(String[] args){
        Record record = new Record();
        record.addId(new Request());
        String b = JsonLink.write(record);
        System.out.println(b);

    }
}
