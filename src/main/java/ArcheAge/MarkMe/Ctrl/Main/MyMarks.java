package ArcheAge.MarkMe.Ctrl.Main;

import ArcheAge.MarkMe.Ctrl.Helpyshka.JsonLink;
import ArcheAge.MarkMe.Ctrl.Helpyshka.SelectWeek;
import ArcheAge.MarkMe.Database.Memders;
import ArcheAge.MarkMe.Database.Sql;
import ArcheAge.MarkMe.Variables.EverydayBosses;
import ArcheAge.MarkMe.Variables.Record;
import ArcheAge.MarkMe.Variables.Request;
import ArcheAge.MarkMe.Variables.ScheduleBosses;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class MyMarks {

    @GetMapping("/myMarks")
    public String getActivities(HttpSession session, Model model) throws SQLException {
        //String nickname = (String) session.getAttribute("nickname");
        String nickname = "Курля";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int id = Memders.getIdFromNickname(nickname);
        LocalDate currentDate = (LocalDate) session.getAttribute("selectedDate");
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        String currentDay = currentDate.getDayOfWeek().toString();
        if (!Sql.existsDate(currentDate)){
            Sql.addDateForActivities(currentDate);
        }

        if (session.getAttribute("imageBoss") != null) {
            System.out.println(session.getAttribute("imageBoss"));
            model.addAttribute("imageBoss", session.getAttribute("imageBoss"));
        }
        EverydayBosses mondayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Monday")), currentDate, "Monday", id);
        EverydayBosses tuesdayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Tuesday")), currentDate, "Tuesday", id);
        EverydayBosses wednesdayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Wednesday")), currentDate, "Wednesday", id);
        EverydayBosses thursdayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Thursday")), currentDate, "Thursday", id);
        EverydayBosses fridayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Friday")), currentDate, "Friday", id);
        EverydayBosses saturdayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Saturday")), currentDate, "Saturday", id);
        EverydayBosses sundayBosses = new EverydayBosses(List.of(ScheduleBosses.getScheduleBosses("Sunday")), currentDate, "Sunday", id);
        model.addAttribute("mondayBosses", mondayBosses);
        model.addAttribute("tuesdayBosses", tuesdayBosses);
        model.addAttribute("wednesdayBosses", wednesdayBosses);
        model.addAttribute("thursdayBosses", thursdayBosses);
        model.addAttribute("fridayBosses", fridayBosses);
        model.addAttribute("saturdayBosses", saturdayBosses);
        model.addAttribute("sundayBosses", sundayBosses);

        model.addAttribute("bossList", ScheduleBosses.getScheduleBosses(currentDay));
        model.addAttribute("nickname", nickname);
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("currentDay", currentDay);
        session.setAttribute("id", id);
        session.setAttribute("nickname", nickname);
        return "Main/myMarks";
    }

    @PostMapping("/activities_time_for_marks")
    public String postActivities(
            @RequestParam(name = "dateInput") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateInput,
            HttpSession session
    ) {

        session.setAttribute("selectedDate", dateInput);
        return "redirect:/myMarks";
    }

    @PostMapping("/activities_mark")
    public String toggleButton(@RequestParam String name, HttpSession session) {
        Map<String, Boolean> buttonsState = (Map<String, Boolean>) session.getAttribute("buttonsState");
        buttonsState.put(name, !buttonsState.get(name));
        return "redirect:/myMarks";
    }
    @PostMapping("/changeFlagMark")
    public String changeMark(@RequestParam String nameBoss, HttpSession session) throws SQLException {
        String[] str = nameBoss.split("#");
        LocalDate selectedDate = LocalDate.parse(str[0]);
        String selectedBoss= str[1];
        int id = (int) session.getAttribute("id");

        if (!Sql.existsDate(selectedDate)){
            Sql.addDateForActivities(selectedDate);
        }

        Record record = JsonLink.read(Sql.getReport(selectedBoss, selectedDate));
        if (record.getStatusFromId(id).equals("wait")) {
            record.delId(id);
            System.out.println("Пользователь удален");
        } else if (record.getStatusFromId(id).equals("notFound")) {
            record.addId(new Request(id, "wait", null, id));
            System.out.println("Пользователь добавлен");
        }
        Sql.addReport(selectedBoss, selectedDate,JsonLink.write(record));
        return "redirect:/myMarks";
    }

}