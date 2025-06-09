package ArcheAge.MarkMe.Ctrl.Moderations;


import ArcheAge.MarkMe.Database.Sql;
import ArcheAge.MarkMe.Variables.DownloadURL;
import ArcheAge.MarkMe.Variables.JsonLink;
import ArcheAge.MarkMe.Variables.Record;
import ArcheAge.MarkMe.Variables.ScheduleBosses;
import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.SQLException;
import java.time.LocalDate;


@Controller
public class Add_activities {
    @GetMapping("/moderation/add_activities")
    public String add_activities(Model model, HttpSession session) throws SQLException {
        String nickname = "Курля";
        boolean isCreateBoss = false;
        /*String nickname = (String) session.getAttribute("nickname");*/

        if(nickname == null) {
                return "Authentication/login";
        }

        LocalDate currentDate = (LocalDate) session.getAttribute("selectedDate");
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }

        String currentDay = currentDate.getDayOfWeek().toString();
        if (!Sql.existsDate(currentDate)){
            Sql.addDateForActivities(currentDate);
        }

        if (session.getAttribute("statusSelect") == null) {
            session.setAttribute("statusSelect", "notSelected");
        }

        if (session.getAttribute("selectedBoss") == null) {
            session.setAttribute("statusSelect", "notSelected");
        }

        if (session.getAttribute("statusSelect") == "created" || session.getAttribute("statusSelect") == "edit") {
            model.addAttribute("urlBoss", session.getAttribute("urlBoss"));
            model.addAttribute("noteBoss", session.getAttribute("noteBoss"));
            model.addAttribute("PvpBoss", session.getAttribute("PvpBoss"));
            model.addAttribute("timeBoss", session.getAttribute("timeBoss"));
            model.addAttribute("masterBoss", session.getAttribute("masterBoss"));
            model.addAttribute("nameBoss", session.getAttribute("nameBoss"));
            model.addAttribute("warningMessege", session.getAttribute("warningMessege"));
            model.addAttribute("imgBoss", DownloadURL.getURL((String) session.getAttribute("urlBoss")));

        }

        model.addAttribute("bossList", ScheduleBosses.getScheduleBosses(currentDay));
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("nickname", nickname);
        model.addAttribute("statusSelect", session.getAttribute("statusSelect"));
        session.setAttribute("currentDate", currentDate);
        session.setAttribute("nickname", nickname);
        //System.out.println(model.getAttribute("url"));
        return "Moderations/add_activities";
    }
    @PostMapping("/activities_time_for_off")
    public String selectDate(
            @RequestParam(name = "dateInput") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate,
            HttpSession session
    ) throws SQLException {
        if (!Sql.existsDate(selectedDate)){
            Sql.addDateForActivities(selectedDate);
        }
        session.setAttribute("statusSelect", "notSelected");
        session.setAttribute("selectedBoss", null);
        session.setAttribute("selectedDate", selectedDate);
        return "redirect:/moderation/add_activities";
    }

    @PostMapping("/select_activities")
    public String selectBoss(@RequestParam String selectedBoss, Model model, HttpSession session) throws SQLException {
        LocalDate currentDate = (LocalDate) session.getAttribute("selectedDate");
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        if (!Sql.existsDate(currentDate)){
            Sql.addDateForActivities(currentDate);
        }
        boolean isBossCreated = JsonLink.read(Sql.getReport(selectedBoss, currentDate)).getIsCreated();
        System.out.println(isBossCreated);
        if (isBossCreated){
            Record record = JsonLink.read(Sql.getReport(selectedBoss, currentDate));
            session.setAttribute("urlBoss", record.getUrl());
            session.setAttribute("createdBoss", record.getIsCreated());
            session.setAttribute("noteBoss", record.getNote());
            if (record.isPvp()) {
                session.setAttribute("PvpBoss", "+");
            } else{
                session.setAttribute("PvpBoss", "-");
            }
            session.setAttribute("timeBoss", record.getBossTime());
            session.setAttribute("masterBoss", record.getMaster());
            session.setAttribute("nameBoss", record.getNameBoss());
            session.setAttribute("statusSelect", "created");
        } else {
            session.setAttribute("statusSelect", "notCreated");

        }
        session.setAttribute("selectedBoss", selectedBoss);
        return "redirect:/moderation/add_activities";
    }
    @PostMapping("/create_activities")
    public String createActivities(Model model, HttpSession session, @RequestParam String urlBoss, @RequestParam String noteBoss, String isPvpBoss, String timeBoss) throws SQLException {
        if (session.getAttribute("selectedBoss") == null){
            session.setAttribute("statusSelect", "notSelected");
            session.setAttribute("warningMessege", "Действие не получилось. Попробуйте ещё раз!");
            return "redirect:/moderation/add_activities";
        }
        String selectedBoss = session.getAttribute("selectedBoss").toString();
        LocalDate selectedDate = (LocalDate) session.getAttribute("currentDate");
        String jsonRecord = Sql.getReport(selectedBoss, selectedDate);

        Record record = JsonLink.read(jsonRecord);
        record.setUrl(urlBoss);
        record.setNote(noteBoss);
        record.setPvp(!(isPvpBoss == null)); ;
        record.setBossTime(timeBoss);         //TODO: Сделать значение по умолчанию
        record.setMaster((String) session.getAttribute("nickname")); //TODO: Поменять ник на ID master
        record.setNameBoss(selectedBoss);
        record.setIsCreated(true);

        session.setAttribute("urlBoss", record.getUrl());
        session.setAttribute("createdBoss", record.getIsCreated());
        session.setAttribute("noteBoss", record.getNote());
        if (record.isPvp()) {
            session.setAttribute("PvpBoss", "+");
        } else{
            session.setAttribute("PvpBoss", "-");
        }
        session.setAttribute("timeBoss", record.getBossTime());
        session.setAttribute("masterBoss", record.getMaster());
        session.setAttribute("nameBoss", record.getNameBoss());
        session.setAttribute("statusSelect", "created");

        Sql.addReport(selectedBoss, selectedDate, JsonLink.write(record));
        return "redirect:/moderation/add_activities";
    }
    @PostMapping("/edit_activities")
    public String editActivities(Model model, HttpSession session){
        session.setAttribute("statusSelect", "edit");

        return "redirect:/moderation/add_activities";
    }
    @PostMapping("/remove_activities")
    public String removeActivities(HttpSession session){
        if (session.getAttribute("selectedBoss") == null){
            session.setAttribute("statusSelect", "notSelected");
            session.setAttribute("warningMessege", "Действие не получилось. Попробуйте ещё раз!");
            return "redirect:/moderation/add_activities";
        }
        String selectedBoss = session.getAttribute("selectedBoss").toString();
        LocalDate selectedDate = (LocalDate) session.getAttribute("currentDate");
        Record record = JsonLink.read(Sql.getReport(selectedBoss, selectedDate));
        record.setIsCreated(false);
        Sql.addReport(selectedBoss, selectedDate, JsonLink.write(record));
        session.setAttribute("statusSelect", "notCreated");

        return "redirect:/moderation/add_activities";
    }
}
