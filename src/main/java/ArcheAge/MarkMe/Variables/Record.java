package ArcheAge.MarkMe.Variables;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Record {
    private List<Integer> id;
    private String link = "";
    private String note = "";
    private boolean isPVP = false;
    private String bossTime = "";
     public Record(List<Integer> id, String link, String note, boolean isPVP, String bossTime) {
        this.id = id;
        this.link = link;
        this.note = note;
        this.isPVP = isPVP;
        this.bossTime = bossTime;
    }
    public void addId(int id) {
        this.id.add(id);
    }
    public void delId(int id) {
        this.id.remove(id);
    }
}
