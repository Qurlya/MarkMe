package ArcheAge.MarkMe.Variables;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    private Boolean needCheck = false;
    private String dateBoss = null;

    public boolean checkNeedCheck(){
        for (Request request : request) {
            if (request.getStatus().equals("wait")){
                needCheck = true;
                break;
            } else {
                needCheck = false;
            }
        }
        return needCheck;
    }

    public void addId(Request request) {
        this.request.add(request);
    }
    public void delId(int id) {
        request.removeIf(request -> request.getId() == id);
    }
    public String getStatusFromId(int id){
        if (request.isEmpty()){
            return "notFound";
        }
        for (Request request : request) {
            if (request.getId() == id){
                return request.getStatus();
            }
        }
        return "notFound";
    }
}
