package ArcheAge.MarkMe.Ctrl.Helpyshka;

import java.time.LocalDate;
import java.util.Locale;

public class SelectWeek {
    public static void main(String[] args) {
        System.out.println(selectWeek(LocalDate.now(), "Wednesday"));
    }
    public static LocalDate selectWeek(LocalDate date, String likeDayOfWeek) {
        LocalDate currentDate = date;
        String day = String.valueOf(date.getDayOfWeek());
        while (!day.equals("MONDAY")) {
            currentDate = currentDate.minusDays(1);
            day = String.valueOf(currentDate.getDayOfWeek());
        }
        while (!day.equals(likeDayOfWeek.toUpperCase(Locale.ROOT))) {
            currentDate = currentDate.plusDays(1);
            day = String.valueOf(currentDate.getDayOfWeek());
        }
        return currentDate;
    }
}
