package ArcheAge.MarkMe.Ctrl.Authentication;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Logout {
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("nickname");
        return "redirect:/";
    }
}
