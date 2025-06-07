package ArcheAge.MarkMe.Ctrl.Moderations;


import ArcheAge.MarkMe.Variables.DownloadURL;
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
    public String add_activities(Model model, HttpSession session) {
        String nickname = "Курля";
        /*String url = DownloadURL.getURL("https://iimg.su/i/YE88KB");*/
        /*String nickname = (String) session.getAttribute("nickname");*/

        /*model.addAttribute("isExist", false);*/
        /*model.addAttribute("img_url", url);*/


        /*System.out.println(session.getAttribute("selectedName"));*/

        if(nickname == null) {
            return "Authentication/login";
        }


        LocalDate currentDate = (LocalDate) session.getAttribute("selectedDate");
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        String currentDay = currentDate.getDayOfWeek().toString();
        for (String i: ScheduleBosses.getScheduleBosses(currentDay)){
            System.out.println(i);
        }

        /*String s = currentDate.toString();*/
        /*if (!Pg.isTableExists(s)){
            Pg.createDay(s, day);
        }*/




        model.addAttribute("bossList", ScheduleBosses.getScheduleBosses(currentDay));
        model.addAttribute("currentDate", currentDate);
        model.addAttribute("nickname", nickname);
        session.setAttribute("currentDate", currentDate);
        /*model.addAttribute("selectedName", session.getAttribute("selectedName"));*/
        return "Moderations/add_activities";
    }
    @PostMapping("/activities_time_for_off")
    public String postActivities(
            @RequestParam(name = "dateInput") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateInputAA,
            RedirectAttributes redirectAttributes,
            HttpSession session
    )  {
        redirectAttributes.addFlashAttribute("selectedDate", dateInputAA);
        /*if (!Pg.isTableExists(String.valueOf(dateInputAA))){
            Pg.createDay(String.valueOf(dateInputAA), String.valueOf(dateInputAA.getDayOfWeek()));
        }*/
        session.setAttribute("selectedDate", dateInputAA);
        return "redirect:/moderation/add_activities";
    }

    @PostMapping("/select_activities")
    public String handleNameSelection(@RequestParam String selectedName, Model model, HttpSession session) throws SQLException {
        model.addAttribute("selectedName", selectedName);
        session.setAttribute("selectedName", selectedName);
        return "redirect:/moderation/add_activities";
    }
    @PostMapping("/create_activities")
    public String create_activities(Model model, HttpSession session){
        return "redirect:/moderation/add_activities";
    }
}
