package ArcheAge.MarkMe.Ctrl.Helpyshka;

import ArcheAge.MarkMe.Database.ActivitiesSQL;
import ArcheAge.MarkMe.Variables.Record;
import ArcheAge.MarkMe.Variables.ScheduleBosses;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Support {
    public static void main(String[] args) throws SQLException {
        getMapFlagBossesFromId(LocalDate.now(), 17);
    }
    public static Map<String, String> getMapFlagBossesFromId(LocalDate localDate, int id) throws SQLException {
        String day = localDate.getDayOfWeek().toString();
        Map<String, String> flagBosses = new HashMap<>();
        Map<String, String> map;
        map = ActivitiesSQL.getActivitiesByDate(localDate);
        map.remove("date");
        Record record;
        for (String name : ScheduleBosses.getScheduleBosses(day)){
            record = JsonLink.read(map.get(name));
            flagBosses.put(name, record.getStatusFromId(id));
        }
        return flagBosses;
    }
}
