package ArcheAge.MarkMe.Ctrl.Moderations;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class All_members {
    @GetMapping("/moderation/all_members")
    public String moderation(HttpSession session, Model model) {
        /*
        String nickname = (String) session.getAttribute("nickname");
*/
        String nickname = "Курля";

        model.addAttribute("nickname", nickname);
        if(nickname == null) {
            return "Authentication/login";
        }

        String[] names = {"Алексей", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга", "Мария", "Иван", "Ольга"}; // Ваш массив имён
        model.addAttribute("names", names);


        return "Moderations/all_members";
    }
    @PostMapping("/select-name")
    public String handleNameSelection(@RequestParam String selectedName, Model model) {
        System.out.println("Выбрано имя: " + selectedName);
        model.addAttribute("selectedName", selectedName);
        return "redirect:/moderation/all_members"; // Страница с результатом выбора
    }
}


