package ArcheAge.MarkMe.Ctrl.Authentication;

import ArcheAge.MarkMe.Database.Pg;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Login {
    @GetMapping("/login")
    public String loginPage() {
        return "Authentication/login";
    }
    @PostMapping("/login")
    public String login(
            @RequestParam String nickname,
            @RequestParam String pin,
            HttpSession session,
            Model model
    ) {
        if (Pg.checkMembers(nickname, pin)) {
            session.setAttribute("nickname", nickname);
            return "redirect:/";
        } else {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
            return "Authentication/login";
        }
    }
}
