package ArcheAge.MarkMe.Ctrl.Main;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
    @GetMapping("/")
    public String homePage(HttpSession session, Model model) {
/*
        String nickname = (String) session.getAttribute("nickname");
*/
        String nickname = "Курля";

        model.addAttribute("nickname", nickname);
        if(nickname == null) {
            return "login";
        }
        return "Main/home";
    }

}