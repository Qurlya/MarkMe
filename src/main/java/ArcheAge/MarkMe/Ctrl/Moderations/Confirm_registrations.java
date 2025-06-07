package ArcheAge.MarkMe.Ctrl.Moderations;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Confirm_registrations {
    @GetMapping("/moderation/confirm_registrations")
    public String moderation(HttpSession session, Model model) {
        /*
        String nickname = (String) session.getAttribute("nickname");
*/
        String nickname = "Курля";

        model.addAttribute("nickname", nickname);
        if(nickname == null) {
            return "login";
        }

        String[] names = {"Алексей", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван"};
        model.addAttribute("names", names);


        return "Moderations/confirm_registrations";
    }
    /*@PostMapping("/select-name")
    public String handleNameSelection(@RequestParam String selectedName, Model model) {
        System.out.println("Выбрано имя: " + selectedName);
        model.addAttribute("selectedName", selectedName);
        return "redirect:/moderation/all_members"; // Страница с результатом выбора
    }*/
}


