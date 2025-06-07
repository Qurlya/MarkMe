package ArcheAge.MarkMe.Variables;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonLink {
    @Getter
    private static String json;
    private static ObjectMapper mapper = new ObjectMapper();


    static {
        try {
            Record record = new Record(List.of(1, 2, 3), "-", "-", true, "18-30");
            json = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println(JsonLink.json);
    }
    public static String getJSON(){
        return json;
    }
}

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
class Record {
    private List<Integer> id;
    private String link;
    private String note;
    private boolean isPVP;
    private String bossTime;
    Record(List<Integer> id, String link, String note, boolean isPVP, String bossTime) {
        this.id = id;
        this.link = link;
        this.note = note;
        this.isPVP = isPVP;
        this.bossTime = bossTime;
    }
}
