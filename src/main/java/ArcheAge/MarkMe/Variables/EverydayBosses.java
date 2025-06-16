package ArcheAge.MarkMe.Variables;

import ArcheAge.MarkMe.Ctrl.Helpyshka.SelectWeek;
import ArcheAge.MarkMe.Ctrl.Helpyshka.Support;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter

public class EverydayBosses {
    private List<String> namesBosses = new ArrayList<>();
    private String dateBoss;
    private String dayBoss;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String dateForSql;
    private Map<String, String> map;
    public EverydayBosses(List<String> namesBosses, LocalDate dateBoss, String dayBoss, int id) throws SQLException {
        this.namesBosses = namesBosses;
        this.dateForSql = dateBoss.toString();
        this.dateBoss = (SelectWeek.selectWeek(dateBoss, dayBoss)).format(formatter);
        this.dayBoss = dayBoss;
        this.map = Support.getMapFlagBossesFromId(dateBoss, id);
    }
}
