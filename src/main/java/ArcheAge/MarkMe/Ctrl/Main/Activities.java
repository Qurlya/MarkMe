package ArcheAge.MarkMe.Ctrl.Main;

import jakarta.servlet.http.HttpSession;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Activities {
    private List<String> names = Arrays.asList("Алексей", "Мария", "Иван", "Елена");
    @GetMapping("/activities")
    public String getActivities(HttpSession session, Model model) {

        //String nickname = (String) session.getAttribute("nickname");
        String nickname = "Курля";
        Boolean isButtonYellow = (Boolean) session.getAttribute("isButtonYellow");
        Map<String, Boolean> buttonsState = (Map<String, Boolean>) session.getAttribute("buttonsState");
        if (buttonsState == null) {
            buttonsState = new HashMap<>();
            Map<String, Boolean> finalButtonsState = buttonsState;
            names.forEach(name -> finalButtonsState.put(name, false));
            session.setAttribute("buttonsState", buttonsState);
        }


        model.addAttribute("isButtonYellow", isButtonYellow);
        model.addAttribute("buttonsState", buttonsState);

        LocalDate currentDate = (LocalDate) session.getAttribute("selectedDate");
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        System.out.println(currentDate);

        model.addAttribute("namesList", names);

        model.addAttribute("nickname", nickname);
        model.addAttribute("currentDate", currentDate);
        /*session.removeAttribute("selectedDate");*/

        return "Main/activities";
    }

    @PostMapping("/activities_time")
    public String postActivities(
            @RequestParam(name = "dateInput") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateInput,
            HttpSession session
    ) {
        session.setAttribute("selectedDate", dateInput);
        return "redirect:/activities";
    }

    @PostMapping("/activities_mark")
    public String toggleButton(@RequestParam String name, HttpSession session) {
        Map<String, Boolean> buttonsState = (Map<String, Boolean>) session.getAttribute("buttonsState");
        buttonsState.put(name, !buttonsState.get(name));
        return "redirect:/activities";
    }


}