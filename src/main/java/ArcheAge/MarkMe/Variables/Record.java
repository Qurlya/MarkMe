package ArcheAge.MarkMe.Variables;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Record {
    private List<Request> request = new ArrayList<>();
    private String url = null;
    private String note = null;
    private boolean pvp = false;
    private String bossTime = null;
    private String master = null;
    private String nameBoss = null;
    @JsonProperty("isCreated")
    private Boolean isCreated = false;


    public void addId(Request request) {
        this.request.add(request);
    }
    public void delId(Request request) {
        this.request.remove(request);
    }
}
